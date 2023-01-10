package com.junpu.data.structure.heap;

/**
 * 堆
 * @author junpu
 * @date 2022/6/24
 */
public interface Heap<T> {
    int size();

    boolean isEmpty();

    void clear();

    /**
     * 获取堆顶元素
     */
    T get();

    /**
     * 添加元素
     */
    void add(T item);

    /**
     * 删除堆顶元素
     */
    T remove();

    /**
     * 替换堆顶元素
     */
    T replace(T item);
}
