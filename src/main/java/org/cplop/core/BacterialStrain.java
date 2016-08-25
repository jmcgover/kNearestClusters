package org.cplop.core;

import java.util.*;

import org.cplop.cluster.Cluster;
import org.cplop.knc.Neighbor;

public class BacterialStrain
    implements Cluster<Isolate>, Neighbor<Isolate> {

    private Set<Isolate> members;

    public BacterialStrain(Collection<Isolate> isolates) {
        this.members = new HashSet<Isolate>(isolates);
    }

    public Set<Isolate> getMembers() {
        return this.members;
    }
    public Boolean isSingletion() {
        return this.members.size() == 1;
    }
    public List<Double> calculateDistance(Isolate other) {
        return null;
    }
}

