package com.junpu.data.structure.tree;

import java.util.Comparator;

/**
 * 平衡二叉搜索树
 * @author junpu
 * @date 2022/4/3
 */
public class BalanceBinarySearchTree<T> extends BinarySearchTree<T> {
    public BalanceBinarySearchTree() {
        this(null);
    }

    public BalanceBinarySearchTree(Comparator<T> comparator) {
        super(comparator);
    }

    /**
     * 左旋
     */
    protected void rotateLeft(Node<T> g) {
        Node<T> p = g.right;
        Node<T> child = p.left;

        g.right = child;
        p.left = g;

        afterRotate(g, p, child);
    }

    /**
     * 右旋
     */
    protected void rotateRight(Node<T> g) {
        Node<T> p = g.left;
        Node<T> child = p.right;

        g.left = child;
        p.right = g;

        afterRotate(g, p, child);
    }

    /**
     * 旋转后
     */
    protected void afterRotate(Node<T> g, Node<T> p, Node<T> child) {
        // 更新 child.parent
        if (child != null) child.parent = g;

        // 更新 p.parent
        if (g.isLeftChild()) {
            g.parent.left = p;
        } else if (g.isRightChild()) {
            g.parent.right = p;
        } else {
            root = p;
        }
        p.parent = g.parent;
        g.parent = p;
    }

}
