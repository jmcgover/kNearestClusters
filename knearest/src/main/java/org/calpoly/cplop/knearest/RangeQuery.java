package org.calpoly.cplop.knearest;

import java.util.List;

public interface RangeQuery<I, S extends Similaritable> {
    public List<NeighborEntry<I, S>> getKNearest(Integer k);
    public List<NeighborEntry<I, S>> getEpsilonNearest(S epsilon);
    public List<NeighborEntry<I, S>> getAllNearest();
}
