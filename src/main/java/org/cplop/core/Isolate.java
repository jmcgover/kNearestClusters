package org.cplop.core;

import java.util.*;

public class Isolate {
    private String isolateId;
    private String hostId;
    private String hostSpeciesName;

    private Map<Integer, List<Double>> pyroprintsITS1;
    private Map<Integer, List<Double>> pyroprintsITS2;

    private List<Double> representativeITS1Pyroprint;
    private List<Double> representativeITS2Pyroprint;
    public Isolate(
            String isolateId,
            String hostId,
            String hostSpeciesName,
            Map<Integer, List<Double>> pyroprintsITS1,
            Map<Integer, List<Double>> pyroprintsITS2) {
        this.isolateId = isolateId;
        this.hostId = hostId;
        this.hostSpeciesName = hostSpeciesName;
        this.pyroprintsITS1 = new Hashtable<Integer, List<Double>>(pyroprintsITS1);
        this.pyroprintsITS2 = new Hashtable<Integer, List<Double>>(pyroprintsITS2);
    }
    public String getIsolateId() {
        return this.isolateId;
    }
    public String getHostSpeciesName() {
        return this.hostSpeciesName;
    }
    public String getHostId() {
        return this.hostId;
    }
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        return this.isolateId.equals(((Isolate)o).isolateId);
    }
    public int hashCode() {
        return this.isolateId.hashCode();
    }
    public String toString() {
        return this.getIsolateId();
    }
}
