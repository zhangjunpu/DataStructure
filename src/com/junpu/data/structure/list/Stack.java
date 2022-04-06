package com.junpu.data.structure.list;

/**
 * 栈
 *
 * @author junpu
 * @date 2022/4/6
 */
public interface Stack<T> {

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
     * 压栈
     */
    void push(T item);

    /**
     * 弹栈
     */
    T pop();

    /**
     * 查看栈顶元素
     */
    T peek();
}
