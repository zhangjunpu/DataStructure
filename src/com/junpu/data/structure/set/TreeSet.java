package com.junpu.data.structure.set;

import com.junpu.data.structure.map.Map;
import com.junpu.data.structure.map.TreeMap;
import com.junpu.data.structure.tree.Visitor;

/**
 * Tree Map
 *
 * @author junpu
 * @date 2022/4/11
 */
public class TreeSet<T> implements Set<T> {

    private final Map<T, Object> map = new TreeMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    @Override
    public void add(T item) {
        map.put(item, null);
    }

    @Override
    public void remove(T item) {
        map.remove(item);
    }

    @Override
    public void traversal(Visitor<T> visitor) {
        if (visitor == null) return;
        map.traversal((key, value) -> visitor.visit(key));
    }

}
