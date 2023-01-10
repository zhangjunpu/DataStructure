package com.junpu.data.structure.list;

import java.util.Arrays;

/**
 * 动态数组
 *
 * @author junpu
 * @date 2022/4/5
 */
public class ArrayList<T> extends AbstractList<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private T[] items;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        int capacity = Math.max(initialCapacity, DEFAULT_CAPACITY);
        items = (T[]) new Object[capacity];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(int index, T item) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(items, index, items, index + 1, size - index);

        items[index] = item;
        size++;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        T oldItem = items[index];
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[--size] = null;
        trim();
        return oldItem;
    }

    @Override
    public T set(int index, T item) {
        rangeCheck(index);
        T oldItem = items[index];
        items[index] = item;
        return oldItem;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return items[index];
    }

    @Override
    public int indexOf(T item) {
        if (item == null) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) return i;
            }
        } else {
            for (int i = 0; i < items.length; i++) {
                if (item.equals(items[i])) return i;
            }
        }
        return -1;
    }

    /**
     * 扩容
     */
    private void ensureCapacity(int minCapacity) {
        int oldCapacity = items.length;
        if (minCapacity <= oldCapacity) return;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // 容量每次扩大 1.5 倍
        T[] newItems = (T[]) new Object[newCapacity];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
        System.out.println("扩容：" + oldCapacity + " -> " + newCapacity);
    }

    /**
     * 缩容
     */
    private void trim() {
        int oldCapacity = items.length;
        if (oldCapacity <= DEFAULT_CAPACITY) return;
        int newCapacity = oldCapacity >> 1;
        if (newCapacity < size) return;
        items = Arrays.copyOf(items, newCapacity);
        System.out.println("缩容：" + oldCapacity + " -> " + newCapacity);
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "size=" + size +
                ", capacity=" + items.length +
                ", items=" + Arrays.toString(items) +
                '}';
    }
}
