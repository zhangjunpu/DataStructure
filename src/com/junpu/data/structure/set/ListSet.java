package com.junpu.data.structure.set;

import com.junpu.data.structure.list.LinkedList;
import com.junpu.data.structure.list.List;
import com.junpu.data.structure.tree.Visitor;

/**
 * @author junpu
 * @date 2022/4/11
 */
public class ListSet<T> implements Set<T> {
    private final List<T> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(T item) {
        return list.contains(item);
    }

    @Override
    public void add(T item) {
        int index = list.indexOf(item);
        if (index != -1) {
            list.set(index, item);
        } else {
            list.add(item);
        }
    }

    @Override
    public void remove(T item) {
        int index = list.indexOf(item);
        if (index != -1) {
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<T> visitor) {
        if (visitor == null) return;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            visitor.visit(list.get(i));
        }
    }

    @Override
    public String toString() {
        return "ListSet{" +
                "list=" + list +
                '}';
    }
}
