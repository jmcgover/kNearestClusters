package org.calpoly.cplop.knearest;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class LinearRangeQuery<I, S extends Similaritable> implements RangeQuery<I, S> {
    private I instance;
    private SimilarityMetric<I, S> similarityMetric;
    private List<NeighborEntry<I, S>> allNeighbors;
    public LinearRangeQuery(
            I instance,
            List<I> instances,
            SimilarityMetric<I, S> similarityMetric) {
        this.instance = instance;
        this.similarityMetric = similarityMetric;
        this.allNeighbors = this.calculateSimilarities(
                instance, instances, similarityMetric);
    }
    /**
     *
     */
    private List<NeighborEntry<I, S>> calculateSimilarities(
            I instance,
            List<I> instances,
            SimilarityMetric<I, S> similarityMetric) {
        /** Allocate List */
        List<NeighborEntry<I, S>> sortedNeighbors =
            new ArrayList<NeighborEntry<I, S>>(instances.size());
        /** Calculate Similarities */
        for (I other : instances) {
            S similarity = similarityMetric.similarity(instance, other);
            sortedNeighbors.add(
                    new NeighborEntry<I, S>(other, similarity));
        }
        /** Sort List */
        Collections.sort(sortedNeighbors);
        /** Return an umondifiable version of the list */
        return Collections.unmodifiableList(sortedNeighbors);
    }
    /**
     *
     */
    public List<NeighborEntry<I, S>> getKNearest(Integer k) {
        return this.allNeighbors.subList(0, k.intValue());
    }
    /**
     *
     */
    public List<NeighborEntry<I, S>> getEpsilonNearest(S epsilon) {
        Integer ndx = 0;
        /** Figure out which are within the epsilon range */
        for (NeighborEntry<I, S> neighbor : this.allNeighbors) {
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
    public List<NeighborEntry<I, S>> getAllNearest() {
        return this.allNeighbors;
    }
}
