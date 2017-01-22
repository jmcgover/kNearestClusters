package org.calpoly.cplop.knearest;

import java.util.List;

public interface RangeQuery<I> {
    public List<NeighborEntry<I>> getKNearest(Integer k);
    public List<NeighborEntry<I>> getEpsilonNearest(Similaritable epsilon);
    public List<NeighborEntry<I>> getAllNearest();
}
