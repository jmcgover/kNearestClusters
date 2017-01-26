package org.calpoly.cplop.knearest;

import java.util.List;

/**
 * Defines the functionality for a factory that creates the proper k-Nearest
 * classifier for class of type C given an instance and a list of training
 * instances, all of type I
 */

public interface KNearestClassifierFactory<I extends Classifiable<C>, C> {
    public KNearestClassifier<I, C> getClassifier(
            I instance,
            List<I> trainingInstances);
}
