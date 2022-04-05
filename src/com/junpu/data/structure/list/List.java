package com.junpu.data.structure.list;

/**
 * @author junpu
 * @date 2022/4/5
 */
public interface List<T> {
    /**
     * 元素的数量
     */
    int size();

    /**
     * 是否为空
     */
    boolean isEmpty();

    /**
     * 清除所有元素
     */
    void clear();

    /**
     * 是否包含某个元素
     */
    boolean contains(T element);

    /**
     * 添加元素到最后面
     */
    void add(T element);

    /**
     * 往index位置添加元素
     */
    void add(int index, T element);

    /**
     * 删除index位置对应的元素
     */
    T remove(int index);

    /**
     * 设置index位置的元素
     */
    T set(int index, T element);

    /**
     * 返回index位置对应的元素
     */
    T get(int index);

    /**
     * 查看元素的位置
     */
    int indexOf(T element);
}
