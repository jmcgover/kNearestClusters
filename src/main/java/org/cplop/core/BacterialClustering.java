package org.cplop.core;

import java.util.*;

import org.cplop.cluster.Cluster;
import org.cplop.cluster.Clustering;
import org.cplop.knc.Neighbor;

public class BacterialClustering
    implements Clustering<Isolate>{

    private Set<Cluster<Isolate>> clusters;
    public BacterialClustering(Collection<Cluster<Isolate>> clusters) {
        this.clusters = new HashSet<Cluster<Isolate>>(clusters);
    }

    public Set<Cluster<Isolate>> getClusters() {
        return this.clusters;
    }
}

