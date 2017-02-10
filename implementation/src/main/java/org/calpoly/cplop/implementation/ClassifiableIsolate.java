package org.calpoly.cplop.implementation;

import org.calpoly.cplop.knearest.Classifiable;
import org.calpoly.cplop.library.Isolate;

public class ClassifiableIsolate implements Classifiable<String> {
    Isolate isolate;
    String species;
    public ClassifiableIsolate(Isolate isolate) {
        this.isolate = isolate;
        this.species = isolate.getHostSpeciesName();
    }
    public ClassifiableIsolate(Isolate isolate, String species) {
        this.isolate = isolate;
        this.species = species;
    }
    public String getClassification() {
        return this.species;
    }
}
