package org.calpoly.cplop.knearest;

/**
 * The ability for some kind of value returned from a SimilarityMetric to be
 * orderable and thus used to define the ordering of a nearest neighbor list,
 * abstractly. Essentially, this creates a class of wrapper objects that can be ordered and compared to each other, but the actual comparison check occurs at runtime by the inheriting class, in order to be able to make a Java List of threshold values used to trim the k-Nearest list even further. As this intends to be a wrapper class, core class functionality should not reside in here, but merely wrap the ability to compare objects in a comparability that the this class checks at runtime.
 */

public abstract class Similaritable implements Comparable<Similaritable> {
    abstract public int compareTo(Similaritable other);
    abstract public Object getValue();
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        return this.getValue().equals(((Similaritable)other).getValue());
    }
}
