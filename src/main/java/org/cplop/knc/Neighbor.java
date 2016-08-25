package org.cplop.knc;

import java.util.*;

public interface Neighbor<T> {
    public List<Double> calculateDistance(T other);
}

