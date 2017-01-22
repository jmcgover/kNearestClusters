package org.calpoly.cplop.knearest;

import java.util.Map;
import java.util.List;

public class SingleKNNListClassifier<I, C>
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
        return null;
    }
    public List<List<I>> getKNearestLists(
            Integer k) {
        return null;
    }
    public List<List<I>> filterLists(
            List<List<I>> lists,
            List<Similaritable> alphas) {
        return null;
    }
}
