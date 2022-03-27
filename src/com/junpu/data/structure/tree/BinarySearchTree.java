package com.junpu.data.structure.tree;

import java.util.Comparator;

/**
 * @author junpu
 * @date 2022/3/22
 */
public class BinarySearchTree<T> extends BinaryTree<T> {
    private Comparator<T> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void add(T item) {
        elementNotNullCheck(item);

        if (root == null) {
            root = new Node<>(item, null);
            size++;
            return;
        }

        Node<T> parent = root;
        Node<T> temp = root;
        int compare = 0;
        while (temp != null) {
            compare = compare(item, temp.item);
            parent = temp;
            if (compare > 0) {
                temp = temp.right;
            } else if (compare < 0) {
                temp = temp.left;
            } else {
                return;
            }
        }
        if (compare > 0) parent.right = new Node<>(item, parent);
        if (compare < 0) parent.left = new Node<>(item, parent);
        size++;
    }

    @Override
    public void remove(T item) {
        remove(node(item));
    }

    /**
     * 删除节点
     * 一、节点度为 0
     * 说明节点为叶子结点
     * 1. 如果是根节点：root = null
     * 2. 节点是父节点的 left：node.parent.left = null
     * 3. 节点是父节点的 right：node.parent.right = null
     *
     * 二、节点度为 1
     * 1. 是根节点: root = child, child.parent = null
     * 2. 是父节点的 left: node.parent.left = child, child.parent = node.parent
     * 3. 是父节点的 right: node.parent.right = child, child.parent = node.parent
     *
     * 三、节点度为 2
     * 1. 找到它的前驱/后继节点 p
     * 2. 替换节点的值为它的前驱/后继节点的值：node.item = p.item
     * 3. 删除前驱/后继节点 p（p 的度一定不为 2）
     */
    private void remove(Node<T> node) {
        if (node == null) return;

        // 如果节点的度为2
        if (node.left != null && node.right != null) {
            Node<T> s = successor(node);
            node.item = s.item;
            node = s;
        }

        Node<T> child = node.left != null ? node.left : node.right;
        if (child == null) { // 说明节点的度为0
            if (node.parent == null) { // 根节点
                root = null;
            } else if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        } else { // 节点的度为1
            child.parent = node.parent;
            if (node.parent == null) { // 根节点
                root = child;
            } else if (node.parent.left == node) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }

        size--;
    }

    @Override
    public boolean contains(T item) {
        return node(item) != null;
    }

    /**
     * 根据 T 查找对应的结点
     */
    private Node<T> node(T item) {
        Node<T> node = root;
        while (node != null) {
            int compare = compare(item, node.item);
            if (compare == 0) {
                return node;
            } else if (compare < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * 检查 item 是否为空
     */
    private void elementNotNullCheck(T item) {
        if (item == null)
            throw new IllegalArgumentException("Item must not be null.");
    }

    private int compare(T t1, T t2) {
        if (comparator != null) return comparator.compare(t1, t2);
        return ((Comparable<T>) t1).compareTo(t2);
    }

}
