package org.calpoly.cplop.knearest;

public class NeighborEntry<I>
    implements Comparable<NeighborEntry<I>> {

    private I instance;
    private Similaritable similarity;
    public NeighborEntry(I instance, Similaritable similarity) {
        this.instance = instance;
        this.similarity = similarity;
    }
    public I getInstance() {
        return this.instance;
    }
    public Similaritable getSimilarity() {
        return this.similarity;
    }
    public int compareTo(NeighborEntry<I> other) {
        return this.similarity.compareTo(other.similarity);
    }
}
