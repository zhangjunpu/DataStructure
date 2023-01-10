package com.junpu.data.structure.heap;

import com.junpu.data.structure.tree.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * @author junpu
 * @date 2022/6/24
 */
public class BinaryHeap<T> implements Heap<T>, BinaryTreeInfo {
    private int size;
    private T[] items;
    private Comparator<T> tComparator;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        this(null);
    }

    public BinaryHeap(Comparator<T> comparator) {
        this.tComparator = comparator;
        items = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                items[i] = null;
            }
            size = 0;
        }
    }

    @Override
    public T get() {
        checkEmpty();
        return items[0];
    }

    /**
     * 添加新元素
     */
    @Override
    public void add(T item) {
        checkItemNotNull(item);
        ensureSize(size + 1);
        items[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * 删除堆顶元素
     */
    @Override
    public T remove() {
        checkEmpty();
        int lastIndex = --size;
        T root = items[0];
        items[0] = items[lastIndex];
        items[lastIndex] = null;
        siftDown(0);
        return root;
    }

    /**
     * 删除堆顶元素，并添加新元素
     */
    @Override
    public T replace(T item) {
        checkItemNotNull(item);
        T root = null;
        if (size == 0) {
            items[size++] = item;
        } else {
            root = items[0];
            items[0] = item;
            siftDown(0);
        }
        return root;
    }

    /**
     * 扩容
     */
    private void ensureSize(int capacity) {
        int len = items.length;
        if (capacity <= len) return;
        int newLen = len + (len >> 1);
        T[] newItems = (T[]) new Object[newLen];
        System.arraycopy(items, 0, newItems, 0, len);
        items = newItems;
    }

    /**
     * 上滤
     */
    private void siftUp(int index) {
        T item = items[index];
        while (index > 0) {
            int pIndex = (index - 1) >> 1;
            T parent = items[pIndex];
            if (compare(item, parent) <= 0) break;
            items[index] = parent;
            index = pIndex;
        }
        items[index] = item;
    }

    /**
     * 下滤
     */
    private void siftDown(int index) {
        int half = size >> 1;
        T item = items[index];
        // index < half，证明 index 有子节点
        while (index < half) {
            // 左子节点
            int childIndex = index * 2 + 1;
            T child = items[childIndex];

            // 右子节点
            int rightChildIndex = childIndex + 1;
            // 有右子节点
            if (rightChildIndex < size) {
                T rightChild = items[rightChildIndex];
                if (compare(rightChild, child) > 0) {
                    childIndex = rightChildIndex;
                    child = rightChild;
                }
            }

            if (compare(item, child) >= 0) break;
            items[index] = child;
            index = childIndex;
        }
        items[index] = item;
    }

    private int compare(T t1, T t2) {
        return tComparator != null ? tComparator.compare(t1, t2) : ((Comparable<T>) t1).compareTo(t2);
    }

    private void checkEmpty() {
        if (size == 0) {
            throw new NullPointerException("items is null");
        }
    }

    private void checkItemNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index < size ? index : null;
    }

    @Override
    public Object right(Object node) {
        int index = ((int) node << 1) + 2;
        return index < size ? index : null;
    }

    @Override
    public Object string(Object node) {
        return items[(int) node];
    }
}
