package org.cplop.cluster;

import java.util.*;

public interface Cluster<T> {
    public Set<T> getMembers();
    public Boolean isSingletion();
}
