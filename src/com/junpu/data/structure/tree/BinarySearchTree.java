package com.junpu.data.structure.tree;

import java.util.Comparator;

/**
 * 二叉搜索树
 *
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
            root = createNode(item, null);
            size++;

            // 新添加节点之后的处理
            afterAdd(root);
            return;
        }

        Node<T> parent, node = root;
        int cmp;
        do {
            cmp = compare(item, node.item);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                return;
            }
        } while (node != null);

        Node<T> newNode = createNode(item, parent);
        if (cmp > 0) parent.right = newNode;
        if (cmp < 0) parent.left = newNode;
        size++;

        afterAdd(newNode);
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
     * <p>
     * 二、节点度为 1
     * 1. 是根节点: root = child, child.parent = null
     * 2. 是父节点的 left: node.parent.left = child, child.parent = node.parent
     * 3. 是父节点的 right: node.parent.right = child, child.parent = node.parent
     * <p>
     * 三、节点度为 2
     * 1. 找到它的前驱/后继节点 s
     * 2. 替换节点的值为它的前驱/后继节点的值：node.item = s.item
     * 3. 删除前驱/后继节点 s（s 的度只可能为 1 或 0）
     */
    private void remove(Node<T> node) {
        if (node == null) return;

        size--;

        // 如果节点的度为2
        if (node.left != null && node.right != null) {
            Node<T> s = successor(node);
            node.item = s.item;
            node = s;
        }

        // 能来到这里，说明 node 节点的度只能是 0 或 1
        Node<T> child = node.left != null ? node.left : node.right;
        if (child != null) { // child 不为空，说明节点的度为1
            child.parent = node.parent;
        }
        if (node.parent == null) { // 根节点
            root = child;
        } else if (node == node.parent.left) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }

        afterRemove(node, child);
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

    /**
     * 添加之后，一般用于子类平衡旋转操作
     */
    protected void afterAdd(Node<T> node) {
    }

    /**
     * 删除之后，用于子类平衡旋转操作
     */
    protected void afterRemove(Node<T> node, Node<T> replacement) {
    }

    /**
     * 创建新的结点
     */
    protected Node<T> createNode(T item, Node<T> parent) {
        return new Node<>(item, parent);
    }

}
