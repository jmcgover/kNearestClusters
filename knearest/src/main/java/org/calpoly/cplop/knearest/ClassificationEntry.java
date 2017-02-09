package org.calpoly.cplop.knearest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassificationEntry<C>
    implements Comparable<ClassificationEntry<C>> {

    private C classification;
    private Integer count;
    private Double averagePosition;
    private List<Integer> positions;

    public ClassificationEntry(C classification, List<Integer> positions) {
        this.classification = classification;
        this.positions = positions;
        this.count = positions.size();

        Double totalPositions = 0.0;
        for (Integer position : positions) {
            totalPositions += position;
        }
        this.averagePosition = totalPositions / this.count;
    }

    public C getClassification() {
        return this.classification;
    }
    public Integer getCount() {
        return this.count;
    }
    public Double getAveragePosition() {
        return this.averagePosition;
    }
    public List<Integer> getPositions() {
        return this.positions;
    }
    public int compareTo(ClassificationEntry<C> other) {
        /**
         * Sort by highest count
         */
        int comparison = this.count.compareTo(other.count);
        if (comparison == 0) {
            /**
             * Sort by lowest average position
             */
            comparison = -1 * this.averagePosition.compareTo(
                    other.averagePosition);
        }
        return comparison;
    }
}
