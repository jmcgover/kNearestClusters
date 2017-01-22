package org.calpoly.cplop.knearest;

/**
 * Defines the necessary functionality for a similarity, distance, or comparison
 * metric to compare instances of type I, returning an orderable (Comparable)
 * similarity value S.
 *
 * @param <I> Type of the instances to calculate the similarity of
 * @param <S> Similarity return value of the calculation
 *            (must have a total ordering)
 */
public interface SimilarityMetric<I> {
    public Similaritable similarity(I a, I b);
}
