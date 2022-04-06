package com.junpu.data.structure.list;

/**
 * 用链表实现的队列
 *
 * @author junpu
 * @date 2022/4/6
 */
public class LinkedQueue<T> implements Queue<T> {
    private List<T> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void offer(T item) {
        list.add(item);
    }

    @Override
    public T poll() {
        return list.remove(0);
    }

    @Override
    public T peek() {
        return list.get(0);
    }
}
