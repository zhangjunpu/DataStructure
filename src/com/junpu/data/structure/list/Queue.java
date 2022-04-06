package com.junpu.data.structure.list;

/**
 * 队列
 *
 * @author junpu
 * @date 2022/4/6
 */
public interface Queue<T> {
    /**
     * 元素的数量
     */
    int size();

    /**
     * 是否为空
     */
    boolean isEmpty();

    /**
     * 清空
     */
    void clear();

    /**
     * 入队
     */
    void offer(T item);

    /**
     * 出队
     */
    T poll();

    /**
     * 获取队列的头元素
     */
    T peek();
}
