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
    public static final String hostSpecies  = "commonName";
    public static final String hostId       = "hostId";
    public static final String isolateId    = "isoId";
    public static final String pyroprintId  = "pyroId";
    public static final String itsRegion    = "appliedRegion";
    public static final String isErroneous  = "isErroneous";
    public static final String peakHeight   = "pHeight";

    /**
     * Properties
     */
    private String queryIsolates;
    private String queryPHeights;
    private String queryZScores;
    private Collection<Map<String, Object>> datapoints;
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
        datapoints = Collections.synchronizedList(new LinkedList<Map<String, Object>>());
        Hashtable<String, Object> datapoint;
        ResultSet resultsPHeights;
        LinkedList<Double> pHeights;
        try {
            while (results.next()) {
                Integer pyroId = results.getInt(pyroprintId);
                datapoint = new Hashtable<String, Object>();
                datapoint.put(hostSpecies,  results.getString(hostSpecies));
                datapoint.put(hostId,       results.getString(hostId));
                datapoint.put(isolateId,    results.getString(isolateId));
                datapoint.put(pyroprintId,  pyroId);
                datapoint.put(itsRegion,    results.getString(itsRegion));
                datapoint.put(isErroneous,  results.getInt(isErroneous) == 1);
                resultsPHeights = db.executeQuery(String.format(queryPHeights, pyroId));
                pHeights = new LinkedList<Double>();
                while (resultsPHeights.next()) {
                    pHeights.add(resultsPHeights.getDouble(peakHeight));
                }
                datapoint.put(peakHeight,   Collections.synchronizedList(new ArrayList<Double>(pHeights)));
                datapoints.add(datapoint);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Collection<Map<String, Object>> getDatapoints() {
        Collection<Map<String, Object>> datapointsCopy;
        datapointsCopy = Collections.synchronizedList(new LinkedList<Map<String, Object>>());
        for (Map<String, Object> m : this.datapoints) {
            datapointsCopy.add(new Hashtable<String, Object>(m));
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
        for (Map<String, Object> datapoint : this.datapoints) {
            System.out.printf("%s\n", datapoint.toString());
        }
    }
}
