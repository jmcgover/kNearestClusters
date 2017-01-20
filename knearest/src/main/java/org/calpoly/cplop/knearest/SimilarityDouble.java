package org.calpoly.cplop.knearest;

public class SimilarityDouble extends Similaritable {
    private Double value;
    public SimilarityDouble(Double value) {
        this.value = value;
    }
    public int compareTo(Similaritable other) {
        if (other.getClass().isInstance(this.getClass())) {
            return this.value.compareTo(((SimilarityDouble)other).value);
        }
        throw new IllegalArgumentException(
                String.format("%s cannot be cast to %s",
                    other.getClass(),
                    this.getClass()));
    }
}
