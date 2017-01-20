package org.calpoly.cplop.knearest;

import java.util.Map;
import java.util.List;

/**
 * Defines the necessary functionality for a k-nearest-type classifier with
 * alpha thresholding to label an instance as a particular class.
 *
 * @param <I> Type of the instances to calculate the class of
 * @param <C> Class type to label the instance as, which must implement a
 *            proper .equals method for future use.
 */
public interface KNearestClassifier<I, C> {
    /**
     * Classifies a given instance of type I into a class of type C, provided
     * k value for the size of the list and an alpha value of type
     * Similaritable to limit the length of the list further.
     *
     * @param instance  Instance of type I to be classified
     * @param k         Integer size of the k-nearest list
     * @param alphas    List of Similarity values corresponding to each
     *                  similarity metric of the instance for threshold filtering
     */
    public C classifyInstance(
            I instance,
            Integer k,
            List<Similaritable> alphas);
    public List<List<I>> getKNearestLists(
            Integer k);
    public List<List<I>> filterLists(
            List<List<I>> lists,
            List<Similaritable> alphas);
}
