package org.calpoly.cplop.knearest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ClassificationCounter<C> {
    public ClassificationCounter() {
    }
    /**
     * Takes a list of classifications and build a list of entries, each
     * containing the count and average position of the classification.
     *
     * @param classifications A list of hashable classifications in the order
     * originally found in the k nearest list.
     */
    public List<ClassificationEntry<C>> countClassifications(
            List<C> classifications) {

        /**
         * Build entries and reverse sort so highest is at the beginning of
         * the list.
         */
        List<ClassificationEntry<C>> entries =
            this.countClassifications(
                classifications,
                Collections.reverseOrder());
        return entries;
    }
    public List<ClassificationEntry<C>> countClassifications(
            List<C> classifications,
            Comparator<ClassificationEntry<C>> comparator) {
        /**
         * Group positions by their classes.
         */
        HashList<C, Integer> positions = new HashList<C, Integer>();
        for (int ndx = 0; ndx < classifications.size(); ndx++) {
            positions.add(classifications.get(ndx), ndx);
        }
        /**
         * Create entries for positions
         */
        List<ClassificationEntry<C>> entries =
            new ArrayList<ClassificationEntry<C>>();
        for (C classification : positions.keySet()) {
            entries.add(
                    new ClassificationEntry<C>(
                        classification,
                        positions.get(classification)));
        }
        if (comparator != null) {
            Collections.sort(entries, comparator);
        }
        return entries;
    }
}
