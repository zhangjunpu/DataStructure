package com.junpu.data.structure.list;

/**
 * List 抽象类
 * @author junpu
 * @date 2022/4/5
 */
public abstract class AbstractList<T> implements List<T> {

    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T item) {
        return indexOf(item) != -1;
    }

    @Override
    public void add(T item) {
        add(size, item);
    }

    /**
     * 范围检查
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds. index=" + index + ", size=" + size);
        }
    }

    /**
     * 范围检查
     */
    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of bounds. index=" + index + ", size=" + size);
        }
    }

}
