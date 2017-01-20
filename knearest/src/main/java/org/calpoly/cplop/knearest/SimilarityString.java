package org.calpoly.cplop.knearest;

public class SimilarityString extends Similaritable {
    private String value;
    public SimilarityString(String value) {
        this.value = value;
    }
    public int compareTo(Similaritable other) {
        if (other.getClass().isInstance(this.getClass())) {
            return this.value.compareTo(((SimilarityString)other).value);
        }
        throw new IllegalArgumentException(
                String.format("%s cannot be cast to %s",
                    other.getClass(),
                    this.getClass()));
    }
}

