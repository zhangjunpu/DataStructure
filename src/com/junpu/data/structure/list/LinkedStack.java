package com.junpu.data.structure.list;


import java.util.EmptyStackException;

/**
 * 用链表实现的栈
 *
 * @author junpu
 * @date 2022/4/6
 */
public class LinkedStack<T> implements Stack<T> {

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
    public void push(T item) {
        list.add(item);
    }

    @Override
    public T pop() {
        checkEmpty();
        return list.remove(list.size() - 1);
    }

    @Override
    public T peek() {
        checkEmpty();
        return list.get(list.size() - 1);
    }

    private void checkEmpty() {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
    }
}
