package org.cplop.builders;

import java.util.Collections;
import java.util.Collection;
import java.util.Map;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.ResultSet;

import org.cplop.db.Database;

public class DataBuilder {
    private final static Logger logger = Logger.getLogger(DataBuilder.class);

    /**
     * Database Constants
     */
    public static final String cplopUrl         = "jdbc:mysql://localhost/CPLOP";
    public static final String cplopUser        = "root";
    public static final String cplopPassword    = "Jeffrey";

    /**
     * Table Constants
     */
    protected static final String hostSpeciesColumn = "commonName";
    protected static final String hostIdColumn      = "hostId";
    protected static final String isolateIdColumn   = "isoId";
    protected static final String pyroprintIdColumn = "pyroId";
    protected static final String itsRegionColumn   = "appliedRegion";
    protected static final String isErroneousColumn = "isErroneous";
    protected static final String peakHeightColumn  = "pHeight";
    protected static final String its1RegionValue   = "16-23";
    protected static final String its2RegionValue   = "23-5";


    /**
     * Formatting DB Results
     */
    private Map<String, String> isolateToSpecies;
    private Map<String, String> isolateToSpeciesEmpty;
    private Map<String, String> isolateToHostId;
    private Map<String, String> isolateToHostIdEmpty;
    protected static final String emptySpeciesValue   = "~NONE~";

    /**
     * Properties
     */
    private String queryIsolates;
    private String queryPHeights;
    private String queryHostIds;
    private String querySpecies;
    private String queryZScores;
    protected Collection<Datapoint> datapoints;
    public DataBuilder() {

        /**
         * Do this by hand because Java 8 still doesn't support multiline
         * string declarations even thought it's ${CURRENT_YEAR}.
         */
        queryIsolates = "SELECT ";
        queryIsolates += "p.appliedRegion, ";
        queryIsolates += "p.pyroId, ";
        queryIsolates += "p.isoID, ";
        queryIsolates += "p.isErroneous ";
        queryIsolates += "FROM pyroprints p, Histograms h ";
        queryIsolates += "WHERE h.pyroID = p.pyroID ";
        queryIsolates += "GROUP BY pyroid ";
        queryIsolates += ";";

        /** My attempt to replicate Eric's query */
        /**
        queryIsolates = "SELECT ";
        queryIsolates += "p.pyroId, ";
        queryIsolates += "p.appliedRegion, ";
        queryIsolates += "p.isoID, ";
        queryIsolates += "p.isErroneous ";
        queryIsolates += "FROM pyroprints p ";
        queryIsolates += "INNER JOIN ";
        queryIsolates += "zScores z ";
        queryIsolates += "USING(pyroID) ";
        queryIsolates += "GROUP BY pyroid ";
        queryIsolates += ";";
        */

        queryPHeights = "SELECT ";
        queryPHeights += "position, pheight, pyroId ";
        queryPHeights += "from Histograms ";
        queryPHeights += "WHERE pyroId = %d ";
        queryPHeights += "ORDER BY position";
        queryPHeights += ";";

        querySpecies = "SELECT ";
        querySpecies += "iso.isoID, ";
        querySpecies += "iso.commonName ";
        querySpecies += "FROM ";
        querySpecies += "Isolates as iso";
        querySpecies += ";";

        queryHostIds = "SELECT ";
        queryHostIds += "isoID, ";
        queryHostIds += "hostID ";
        queryHostIds += "FROM ";
        queryHostIds += "Isolates";
        queryHostIds += ";";

        /**
         * Connect to Database
         */
        logger.info("Connecting to database...");
        logger.debug("URL: " + cplopUrl);
        logger.debug("User: " + cplopUser);
        logger.debug("Password : " + cplopPassword);

        Database db = new Database(cplopUrl, cplopUser, cplopPassword);

        /**
         * Query Species
         */
        ResultSet results;
        try {
            logger.debug("Running Query: '" + querySpecies + "'");
            results = db.executeQuery(querySpecies);
        } catch (SQLException e) {
            logger.error("Caught SQLException. Throwing...", e);
            throw new RuntimeException(e);
        }

        Map<String, String> isolateToSpecies = new Hashtable<String, String>();
        Map<String, String> isolateToSpeciesEmpty = new Hashtable<String, String>();
        try {
            String species;
            String isolateId;
            while (results.next()) {
                species = results.getString(hostSpeciesColumn);
                isolateId = results.getString(isolateIdColumn);
                isolateToSpecies.put(isolateId, species);

            }
        } catch (SQLException e) {
            logger.error("Caught SQLException. Throwing...", e);
            throw new RuntimeException(e);
        }
        isolateToSpeciesEmpty.put("Ck-001", "Chicken");
        isolateToSpeciesEmpty.put("Ck-002", "Chicken");
        isolateToSpeciesEmpty.put("Ck-003", "Chicken");
        isolateToSpeciesEmpty.put("Ck-004", "Chicken");
        isolateToSpeciesEmpty.put("Ck-005", "Chicken");
        isolateToSpeciesEmpty.put("CW-1868", "Cow");
        isolateToSpeciesEmpty.put("Hu-077", "Human");
        isolateToSpeciesEmpty.put("Hu-078", "Human");

        /**
         * Query Hosts
         */
        try {
            logger.debug("Running Query: '" + queryHostIds + "'");
            results = db.executeQuery(queryHostIds);
        } catch (SQLException e) {
            logger.error("Caught SQLException. Throwing...", e);
            throw new RuntimeException(e);
        }
        isolateToHostId = new Hashtable<String, String>();
        isolateToHostIdEmpty = new Hashtable<String, String>();
        try {
            String hostId;
            String isolateId;
            while (results.next()) {
                hostId = results.getString(hostIdColumn);
                isolateId = results.getString(isolateIdColumn);
                isolateToHostId.put(isolateId, hostId);

            }
        } catch (SQLException e) {
            logger.error("Caught SQLException. Throwing...", e);
            throw new RuntimeException(e);
        }

        /**
         * Query Isolates
         */
        try {
            logger.debug("Running Query: '" + queryIsolates + "'");
            results = db.executeQuery(queryIsolates);
        } catch (SQLException e) {
            logger.error("Caught SQLException. Throwing...", e);
            throw new RuntimeException(e);
        }
        logger.info("Connection successful!");
        /**
         * Loop through and save all the queried Isolates
         */
        datapoints = Collections.synchronizedList(new LinkedList<Datapoint>());
        Datapoint datapoint;
        ResultSet resultsPHeights;
        LinkedList<Double> pHeights;
        try {
            while (results.next()) {
                /**
                 * Get Pyroprint Data
                 */
                Integer pyroId = results.getInt(pyroprintIdColumn);
                String pyroQuery = String.format(queryPHeights, pyroId);
                logger.debug("Running Query: '" + pyroQuery + "'");
                resultsPHeights = db.executeQuery(pyroQuery);
                pHeights = new LinkedList<Double>();
                while (resultsPHeights.next()) {
                    pHeights.add(resultsPHeights.getDouble(peakHeightColumn));
                }
                /**
                 * Get all other metadata and store.
                 */
                String isolateId = results.getString(isolateIdColumn);
                /**
                 * Isolate to Species
                 */
                String species;
                if (isolateToSpecies.containsKey(isolateId)) {
                    species = isolateToSpecies.get(isolateId);
                } else if (isolateToSpeciesEmpty.containsKey(isolateId)) {
                    species = isolateToSpeciesEmpty.get(isolateId);
                    logger.warn(
                            String.format("Isolate %s missing species. Filling in with %s.", 
                                isolateId, species));
                } else {
                    species = null;
                    logger.fatal(
                        String.format(
                        "Isolate %s species not in replacements. Filling in with %s.",
                            isolateId, species));
                }
                /**
                 * Isolate to HostId
                 */
                String hostId;
                if (isolateToHostId.containsKey(isolateId)) {
                    hostId = isolateToHostId.get(isolateId);
                } else if (isolateToHostIdEmpty.containsKey(isolateId)) {
                    hostId = isolateToHostIdEmpty.get(isolateId);
                    logger.warn(
                            String.format("Isolate %s missing Host ID. Filling in with %s.", 
                                isolateId, hostId));
                } else {
                    hostId = emptySpeciesValue;
                    logger.fatal(
                        String.format(
                        "Isolate %s Host ID not in replacements. Filling in with %s.",
                            isolateId, hostId));
                }
                /**
                 * Build Isolate Datapoint
                 */
                datapoint = new Datapoint(
                    species,
                    hostId,
                    isolateId,
                    pyroId,
                    results.getString(itsRegionColumn),
                    results.getInt(isErroneousColumn) == 1,
                    Collections.synchronizedList(new ArrayList<Double>(pHeights)));
                datapoints.add(datapoint);
            }
        } catch (SQLException e) {
            logger.error("Caught SQLException. Throwing...", e);
            throw new RuntimeException(e);
        }
    }
    public Collection<Datapoint> getDatapoints() {
        Collection<Datapoint> datapointsCopy;
        datapointsCopy = Collections.synchronizedList(new LinkedList<Datapoint>());
        for (Datapoint d : this.datapoints) {
            datapointsCopy.add(new Datapoint(d));
        }
        return datapointsCopy;
    }
    public String getQueryIsolates() {
        return this.queryIsolates;
    }
    public String getQueryPHeights() {
        return this.queryPHeights;
    }
    public String getQueryZScores() {
        return this.queryZScores;
    }
    public void printData(PrintStream out) {
        for (Datapoint datapoint : this.datapoints) {
            System.out.printf("%s\n", datapoint.toString());
        }
    }
}
