package org.calpoly.cplop.knearest;

public class SimilaritableString extends Similaritable {
    private String value;
    public SimilaritableString(String value) {
        this.value = value;
    }
    public int compareTo(Similaritable other) {
        if (other.getClass() == this.getClass()) {
            return this.value.compareTo(
                    ((SimilaritableString)other).value);
        }
        throw new IllegalArgumentException(
                String.format("%s is not %s",
                    other.getValue().getClass(),
                    this.getValue().getClass()));
    }
    public Object getValue() {
        return this.value;
    }
}
