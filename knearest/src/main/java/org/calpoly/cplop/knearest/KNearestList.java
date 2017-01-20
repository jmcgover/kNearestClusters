package org.calpoly.cplop.knearest;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class KNearestList<I, S extends Similaritable> {
    private ArrayList<NeighborEntry<I, S>> neighbors;
    public KNearestList() {
    }
    public List<NeighborEntry<I, S>> getKNearest(Integer k) {
        return this.neighbors.subList(0,k);
    }
    public void sort() {
        Collections.sort(this.neighbors);
    }
}
