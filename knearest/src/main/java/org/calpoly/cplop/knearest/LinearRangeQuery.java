package org.calpoly.cplop.knearest;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class LinearRangeQuery<I> implements RangeQuery<I> {
    private I instance;
    private SimilarityMetric<I> similarityMetric;
    private List<NeighborEntry<I>> allNeighbors;
    public LinearRangeQuery(
            I instance,
            List<I> instances,
            SimilarityMetric<I> similarityMetric) {
        this.instance = instance;
        this.similarityMetric = similarityMetric;
        this.allNeighbors = this.calculateSimilarities(
                instance, instances, similarityMetric);
    }
    /**
     *
     */
    private List<NeighborEntry<I>> calculateSimilarities(
            I instance,
            List<I> instances,
            SimilarityMetric<I> similarityMetric) {
        /** Allocate List */
        List<NeighborEntry<I>> sortedNeighbors =
            new ArrayList<NeighborEntry<I>>(instances.size());
        /** Calculate Similarities */
        for (I other : instances) {
            Similaritable similarity =
                similarityMetric.similarity(instance, other);
            sortedNeighbors.add(
                    new NeighborEntry<I>(other, similarity));
        }

        /** Sort List */
        Collections.sort(sortedNeighbors);

        /** Return an umondifiable version of the list */
        return Collections.unmodifiableList(sortedNeighbors);
    }
    /**
     *
     */
    public List<NeighborEntry<I>> getKNearest(Integer k) {
        return this.allNeighbors.subList(0, k.intValue());
    }
    /**
     *
     */
    public List<NeighborEntry<I>> getEpsilonNearest(Similaritable epsilon) {
        Integer ndx = 0;
        /** Figure out which are within the epsilon range */
        for (NeighborEntry<I> neighbor : this.allNeighbors) {
            ++ndx;
            if (!(neighbor.getSimilarity().compareTo(epsilon) < 0)) {
                break;
            }
        }
        return this.allNeighbors.subList(0, ndx.intValue());
    }
    /**
     *
     */
    public List<NeighborEntry<I>> getAllNearest() {
        return this.allNeighbors;
    }
}
