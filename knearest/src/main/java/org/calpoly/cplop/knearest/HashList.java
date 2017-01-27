package org.calpoly.cplop.knearest;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class HashList<K, V> extends HashMap<K, List<V>> {
    public V add(K key, V value) {
        List<V> list = this.get(key);
        if (list == null) {
            list = new ArrayList<V>();
        }
        list.add(value);
        this.put(key, list);
        return list.get(list.size() - 1);
    }
    public V get(K key, Integer ndx) {
        List<V> list = this.get(key);

        if (list == null) {
            return null;
        }

        return list.get(ndx);
    }
}

