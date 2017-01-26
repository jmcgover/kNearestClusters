package org.calpoly.cplop.knearest;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Hashtable;

public class LinearCachedMetricQuery<I>
    implements RangeQuery<I>, SimilarityMetric<I> {

    private I instance;
    private SimilarityMetric<I> similarityMetric;

    private List<NeighborEntry<I>> allNeighbors;
    private Map<I, Map<I, Similaritable>> similarityCache;

    public LinearCachedMetricQuery(
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
    public List<NeighborEntry<I>> getKNearest(I instance, Integer k) {
        if (!instance.equals(this.instance)) {
            throw new IllegalArgumentException(String.format(
                        "%s must be %s",
                        instance,
                        this.instance));
        }
        return this.allNeighbors.subList(0, k.intValue());
    }
    /**
     *
     */
    public List<NeighborEntry<I>>
        getEpsilonNearest(I instance, Similaritable epsilon) {
        if (!instance.equals(this.instance)) {
            throw new IllegalArgumentException(String.format(
                        "%s must be %s",
                        instance,
                        this.instance));
        }
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
    public List<NeighborEntry<I>> getAllNearest(I instance) {
        if (!instance.equals(this.instance)) {
            throw new IllegalArgumentException(String.format(
                        "%s must be %s",
                        instance,
                        this.instance));
        }
        return this.allNeighbors;
    }
    public Similaritable similarity(I a, I b) {

        /** Retrieve Cache for a */
        Map<I, Similaritable> aSimilarities = this.similarityCache.get(a);
        if (aSimilarities == null) {
            aSimilarities = new Hashtable<I, Similaritable>();
            this.similarityCache.put(a, aSimilarities);
        }

        /** Retrieve Similarity */
        Similaritable similarity;
        similarity = aSimilarities.get(b);
        if (similarity == null) {
            /** Calculate and Cache Simlarity */
            similarity = this.similarityMetric.similarity(a, b);
            aSimilarities.put(b, similarity);
        }
        return similarity;
    }
}

