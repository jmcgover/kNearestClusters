package org.calpoly.cplop.knearest;

public class NeighborEntry<I, S extends Similaritable>
    implements Comparable<NeighborEntry<I, S>> {

    private I instance;
    private S similarity;
    public NeighborEntry(I instance, S similarity) {
        this.instance = instance;
        this.similarity = similarity;
    }
    public I getInstance() {
        return this.instance;
    }
    public S getSimilarity() {
        return this.similarity;
    }
    public int compareTo(NeighborEntry<I, S> other) {
        return this.similarity.compareTo(other.similarity);
    }
}
