package org.calpoly.cplop.knearest;

import java.util.List;

/**
 * Defines the necessary functionality for returning a list of similarity
 * metrics between instances of type I and having an orderable return type of S
 * that is a ``Similaritable'' object.
 *
 * @param <I> Type of the instances to calculate the similarity of
 * @param <S> Similarity return value of the calculation
 *            (must have a total ordering)
 */
public interface SimilarityMetricFactory<I> {
    public List<SimilarityMetric<I>> getSimilarityMetrics();
}
