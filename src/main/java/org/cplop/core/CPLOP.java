package org.cplop.core;

import java.util.*;

public class CPLOP {
    private Collection<Isolate> isolates;
    public CPLOP(Collection<Isolate> isolates) {
        this.isolates = Collections.synchronizedSet(new HashSet<Isolate>(isolates));
    }
}
