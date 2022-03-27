package com.junpu.data.structure.tree;

/**
 * @author junpu
 * @date 2022/3/22
 */
public interface Tree<T> {
    int size();
    boolean isEmpty();
    void clear();
    void add(T item);
    void remove(T item);
    boolean contains(T item);
}
