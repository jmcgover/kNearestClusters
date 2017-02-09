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
    public int compareTo(Similaritable other) {
        if (other.getClass() == this.getClass()) {
            return this.value.compareTo(
                    ((SimilaritablePearson)other).value);
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

