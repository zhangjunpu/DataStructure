package com.junpu.data.structure.tree;

import java.util.Comparator;

/**
 * @author junpu
 * @date 2022/3/27
 */
public class AVLTree<T> extends BinarySearchTree<T> {

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<T> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) { // 节点平衡
                // 更新高度
                avlNode(node).updateHeight();
            } else { // 节点不平衡
                // 恢复平衡
                restoreBalance(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<T> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) { // 节点平衡
                // 更新高度
                avlNode(node).updateHeight();
            } else { // 节点不平衡
                // 恢复平衡
                restoreBalance(node);
            }
        }
    }

    /**
     * 恢复平衡
     */
    private void restoreBalance(Node<T> node) {
        AVLNode<T> g = avlNode(node);
        AVLNode<T> p = avlNode(g.tallerChild());
        AVLNode<T> n = avlNode(p.tallerChild());

        if (p.isLeftChild()) { // L
            if (n.isLeftChild()) { // LL
                rotateRight(g);
            } else { // LR
                rotateLeft(p);
                rotateRight(g);
            }
        } else { // R
            if (n.isRightChild()) { // RR
                rotateLeft(g);
            } else { // RL
                rotateRight(p);
                rotateLeft(g);
            }
        }
    }

    /**
     * 左旋
     */
    private void rotateLeft(AVLNode<T> g) {
        AVLNode<T> p = avlNode(g.right);
        Node<T> child = p.left;

        g.right = child;
        p.left = g;

        afterRotate(g, p, child);
    }

    /**
     * 右旋
     */
    private void rotateRight(AVLNode<T> g) {
        AVLNode<T> p = avlNode(g.left);
        Node<T> child = p.right;

        g.left = child;
        p.right = g;

        afterRotate(g, p, child);
    }

    /**
     * 旋转后
     */
    private void afterRotate(AVLNode<T> g, AVLNode<T> p, Node<T> child) {
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

        // 更新高度
        g.updateHeight();
        p.updateHeight();
    }

    @Override
    protected Node<T> createNode(T item, Node<T> parent) {
        return new AVLNode<>(item, parent);
    }

    /**
     * 节点是否平衡
     */
    private boolean isBalanced(Node<T> node) {
        return Math.abs(avlNode(node).balanceFactor()) <= 1;
    }

    /**
     * 强制转换
     */
    private static <T> AVLNode<T> avlNode(Node<T> node) {
        return (AVLNode<T>) node;
    }

    private static class AVLNode<T> extends Node<T> {
        int height;

        public AVLNode(T item, Node<T> parent) {
            super(item, parent);
            height = 1; // 新创建的节点一定为叶子节点，高度为 1
        }

        /**
         * 节点的平衡因子
         */
        public int balanceFactor() {
            return leftHeight() - rightHeight();
        }

        /**
         * 更新高度
         */
        public void updateHeight() {
            height = Math.max(leftHeight(), rightHeight()) + 1;
        }

        /**
         * 找出子节点中 height 更高的那个
         * 如果一边高，就找出自己跟父节点同一边的那个
         */
        public Node<T> tallerChild() {
            int l = leftHeight();
            int r = rightHeight();
            if (l > r) return left;
            if (l < r) return right;
            return isLeftChild() ? left : right;
        }

        /**
         * 左子节点的高度
         */
        private int leftHeight() {
            return left == null ? 0 : avlNode(left).height;
        }

        /**
         * 右子节点的高度
         */
        private int rightHeight() {
            return right == null ? 0 : avlNode(right).height;
        }

    }
}
