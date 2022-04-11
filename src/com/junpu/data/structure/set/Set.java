package com.junpu.data.structure.set;

import com.junpu.data.structure.tree.Visitor;

/**
 * @author junpu
 * @date 2022/4/11
 */
public interface Set<T> {
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(T item);
    void add(T item);
    void remove(T item);
    void traversal(Visitor<T> visitor);
}
