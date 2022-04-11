package com.junpu.data.structure.tree;

/**
 * @author junpu
 * @date 2022/4/11
 */
public interface Visitor<T> {
    boolean visit(T item);
}
