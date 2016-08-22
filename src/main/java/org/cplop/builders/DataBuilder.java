package org.cplop.builders;

import java.util.Collections;
import java.util.Collection;
import java.util.Map;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.PrintStream;

import java.sql.SQLException;
import java.sql.ResultSet;

import org.cplop.db.Database;

public class DataBuilder {

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
     * Properties
     */
    private String queryIsolates;
    private String queryPHeights;
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
        queryIsolates += "p.isErroneous, ";
        queryIsolates += "i.hostId, ";
        queryIsolates += "i.commonName ";
        queryIsolates += "FROM pyroprints p, Histograms h, Isolates i ";
        queryIsolates += "WHERE h.pyroID = p.pyroID and i.isoId = p.isoId ";
        queryIsolates += "GROUP BY pyroid ";
        queryIsolates += "ORDER BY commonName, hostId, isoId, pyroId, appliedRegion, isErroneous;";

        queryPHeights = "SELECT ";
        queryPHeights += "position, pheight, pyroId ";
        queryPHeights += "from Histograms ";
        queryPHeights += "WHERE pyroId = %d ";
        queryPHeights += "ORDER BY position;";

        /**
         * Connect to Database and Query
         */
        Database db = new Database(cplopUrl, cplopUser, cplopPassword);
        ResultSet results;
        try {
            results = db.executeQuery(queryIsolates);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                resultsPHeights = db.executeQuery(String.format(queryPHeights, pyroId));
                pHeights = new LinkedList<Double>();
                while (resultsPHeights.next()) {
                    pHeights.add(resultsPHeights.getDouble(peakHeightColumn));
                }
                /**
                 * Get all other metadata and store.
                 */
                datapoint = new Datapoint(
                    results.getString(hostSpeciesColumn),
                    results.getString(hostIdColumn),
                    results.getString(isolateIdColumn),
                    pyroId,
                    results.getString(itsRegionColumn),
                    results.getInt(isErroneousColumn) == 1,
                    Collections.synchronizedList(new ArrayList<Double>(pHeights)));
                datapoints.add(datapoint);
            }
        } catch (SQLException e) {
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
