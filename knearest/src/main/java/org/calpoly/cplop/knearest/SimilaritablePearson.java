package org.calpoly.cplop.knearest;

public class SimilaritablePearson extends Similaritable {
    private Double value;
    public SimilaritablePearson(Double value) {
        if (value.doubleValue() > 1.000 || value.doubleValue() < -1.000) {
            throw new IllegalArgumentException(String.format(
                        "%.3f is outside the allowable " +
                        "Pearson Correlation value range",
                        value.doubleValue()));
        }
        this.value = value;
    }
    /**
     * Comparing Pearson values is only useful as they relate to the number 1,
     * due to the nature of this simlarity value. Given a target t and two
     * Pearson Correlation values with respect to t, a and b, if 1 - a < 1 - b,
     * then a is closer to t, in the same way that given two Euclidean Distance
     * values with respect to t, the value closest to 0 is the closer vector.
     */
    public int compareTo(Similaritable other) {
        if (other.getClass() == this.getClass()) {
            Double aDistance = 1.0 - this.value;
            Double bDistance = 1.0 - ((SimilaritablePearson)other).value;
            return aDistance.compareTo(bDistance);
        }
        throw new IllegalArgumentException(
                String.format("%s cannot be cast to %s",
                    other.getValue().getClass(),
                    this.getValue().getClass()));
    }
    public Object getValue() {
        return this.value;
    }
}

