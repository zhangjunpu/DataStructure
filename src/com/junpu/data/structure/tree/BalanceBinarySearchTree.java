package com.junpu.data.structure.tree;

import java.util.Comparator;

/**
 * 平衡二叉搜索树
 *
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
    protected void rotateLeft(Node<T> grand) {
        Node<T> parent = grand.right;
        Node<T> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋
     */
    protected void rotateRight(Node<T> grand) {
        Node<T> parent = grand.left;
        Node<T> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 旋转后
     */
    protected void afterRotate(Node<T> grand, Node<T> parent, Node<T> child) {
        // 更新 child.parent
        if (child != null) child.parent = grand;

        // 更新 p.parent
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }
        parent.parent = grand.parent;
        grand.parent = parent;
    }

}
