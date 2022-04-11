package com.junpu.data.structure.set;

import com.junpu.data.structure.tree.RedBlackTree;
import com.junpu.data.structure.tree.Visitor;

import java.util.Comparator;

/**
 * @author junpu
 * @date 2022/4/11
 */
public class TreeSet<T> implements Set<T> {

    private final RedBlackTree<T> tree;

    public TreeSet() {
        this(null);
    }

    public TreeSet(Comparator<T> comparator) {
        tree = new RedBlackTree<>(comparator);
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(T item) {
        return tree.contains(item);
    }

    @Override
    public void add(T item) {
        tree.add(item);
    }

    @Override
    public void remove(T item) {
        tree.remove(item);
    }

    @Override
    public void traversal(Visitor<T> visitor) {
        if (visitor == null) return;
        tree.inorderTraversal(visitor);
    }

}
