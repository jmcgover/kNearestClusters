package org.calpoly.cplop.knearest;

/**
 * Defines the necessary functionality for a similarity, distance, or comparison
 * metric to compare instances of type I, returning an orderable (Comparable)
 * similarity value S.
 *
 * @param <I> Type of the instances to calculate the similarity of
 * @param <S> Similarity return value of the calculation
 *(which must have a total ordering)
 */
public interface SimilarityMetric<I, S extends Similaritable> {
    abstract public S similarity(I a, I b);
}
