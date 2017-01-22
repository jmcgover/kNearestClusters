package org.calpoly.cplop.knearest;

public class SimilaritableDouble extends Similaritable {
    private Double value;
    public SimilaritableDouble(Double value) {
        this.value = value;
    }
    public int compareTo(Similaritable other) {
        if (other.getClass() == this.getClass()) {
            return this.value.compareTo(
                    ((SimilaritableDouble)other).value);
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
