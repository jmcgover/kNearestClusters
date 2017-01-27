package org.calpoly.cplop.knearest;

import java.util.List;
import java.util.HashMap;

public class Counter<T> extends HashMap<T, Integer> {
    public Counter(List<T> things) {
        for (T thing : things) {
            this.increment(thing);
        }
    }
    public Integer increment(T thing) {
        return this.add(thing, 1);
    }
    public Integer decrement(T thing) {
        return this.add(thing, -1);
    }
    private Integer add(T thing, Integer num) {
        Integer count = super.get(thing);
        if (count == null) {
            count = 0;
        }
        count += num;
        super.put(thing, count);
        return count;
    }
}

