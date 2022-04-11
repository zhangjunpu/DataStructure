package com.junpu.data.structure.list;

/**
 * 双向链表
 *
 * @author junpu
 * @date 2022/4/5
 */
public class LinkedList<T> extends AbstractList<T> {
    private Node<T> first;
    private Node<T> last;

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public void add(int index, T item) {
        rangeCheckForAdd(index);

        if (index == 0) {
            Node<T> next = first;
            first = new Node<>(null, item, next);
            if (next != null) {
                next.prev = first;
            } else { // size == 0
                last = first;
            }
        } else {
            Node<T> prev = node(index - 1);
            Node<T> next = prev.next;
            Node<T> node = new Node<>(prev, item, next);
            prev.next = node;
            if (next != null) {
                next.prev = node;
            } else { // index == size
                last = node;
            }
        }
        size++;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);

        Node<T> node = node(index);
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }

        size--;
        return node.item;
    }

    @Override
    public T set(int index, T item) {
        Node<T> node = node(index);
        T oleItem = node.item;
        node.item = item;
        return oleItem;
    }

    @Override
    public T get(int index) {
        return node(index).item;
    }

    @Override
    public int indexOf(T item) {
        Node<T> node = first;
        if (item == null) {
            for (int i = 0; i < size; i++) {
                if (node.item == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (item.equals(node.item)) return i;
                node = node.next;
            }
        }
        return -1;
    }

    /**
     * 获取 index 位置的节点
     */
    private Node<T> node(int index) {
        rangeCheck(index);
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            sb.append(node);
            if (i < size - 1) sb.append(", ");
            node = node.next;
        }
        sb.append("]");
        return "size=" + size + ", " + sb;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(T item) {
            this.item = item;
        }

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            Object p = prev != null ? prev.item : null;
            Object n = next != null ? next.item : null;
            return p + "_" + item + "_" + n;
        }
    }
}
