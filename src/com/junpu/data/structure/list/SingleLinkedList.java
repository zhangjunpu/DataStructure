package com.junpu.data.structure.list;

/**
 * 单链表
 *
 * @author junpu
 * @date 2022/4/5
 */
public class SingleLinkedList<T> extends AbstractList<T> {
    private Node<T> first;

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public void add(int index, T item) {
        rangeCheckForAdd(index);

        if (index == 0) {
            first = new Node<>(item, first);
        } else {
            Node<T> prev = node(index - 1);
            prev.next = new Node<>(item, prev.next);
        }
        size++;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        Node<T> removeNode;
        if (index == 0) {
            removeNode = first;
            first = first.next;
        } else {
            Node<T> prev = node(index - 1);
            removeNode = prev.next;
            prev.next = removeNode.next;
        }
        size--;
        return removeNode.item;
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
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            sb.append(node.item);
            if (i < size - 1) sb.append(",");
            node = node.next;
        }
        sb.append("]");
        return "size=" + size + ", " + sb;
    }

    private static class Node<T> {
        T item;
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }
}
