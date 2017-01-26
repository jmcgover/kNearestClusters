package org.calpoly.cplop.knearest;

import java.util.List;

public interface RangeQuery<I> {
    public List<NeighborEntry<I>> getKNearest(I instance, Integer k);
    public List<NeighborEntry<I>> getEpsilonNearest(I instance, Similaritable epsilon);
    public List<NeighborEntry<I>> getAllNearest(I instance);
}
