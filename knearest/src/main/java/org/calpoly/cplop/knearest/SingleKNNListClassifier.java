package org.calpoly.cplop.knearest;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

public class SingleKNNListClassifier<I extends Classifiable<C>, C>
    implements KNearestClassifier<I, C>{

    RangeQuery<I> rangeQuery;
    public SingleKNNListClassifier(
            RangeQuery<I> rangeQuery) {
        this.rangeQuery = rangeQuery;
    }
    public C classifyInstance(
            I instance,
            Integer k,
            List<Similaritable> alphas) {

        List<NeighborEntry<I>> kNearestNeighbors = this.rangeQuery.getKNearest(instance, k);
        if (alphas != null) {
            this.filterNeighbors(kNearestNeighbors, alphas.get(0));
        }

        Counter<C> counter = new Counter<C>();
        for (NeighborEntry<I> neighbor : kNearestNeighbors) {
            counter.increment(neighbor.getInstance().getClassification());
        }
        C classification = null;

        return classification;
    }
    private List<NeighborEntry<I>> filterNeighbors(
            List<NeighborEntry<I>> neighbors,
            Similaritable alpha) {
        List<NeighborEntry<I>> filteredNeighbors = new ArrayList<NeighborEntry<I>>();
        for (NeighborEntry<I> neighbor : neighbors) {
            /**
             * Read as: if (sim(instance, neighbor) < alpha)
             */
            if (neighbor.getSimilarity().compareTo(alpha) < 0) {
                filteredNeighbors.add(neighbor);
            } else {
                break;
            }
        }
        return Collections.unmodifiableList(filteredNeighbors);
    }
}
