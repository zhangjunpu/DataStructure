package com.junpu.data.structure.tree;

import java.util.Comparator;

/**
 * AVL树
 *
 * @author junpu
 * @date 2022/3/27
 */
public class AVLTree<T> extends BalanceBinarySearchTree<T> {

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
                updateHeight(node);
            } else { // 节点不平衡
                // 恢复平衡
                restoreBalance(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<T> node, Node<T> replacement) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) { // 节点平衡
                // 更新高度
                updateHeight(node);
            } else { // 节点不平衡
                // 恢复平衡
                restoreBalance(node);
            }
        }
    }

    /**
     * 恢复平衡
     */
    private void restoreBalance(Node<T> g) {
        Node<T> p = ((AVLNode<T>) g).tallerChild();
        Node<T> n = ((AVLNode<T>) p).tallerChild();

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

    @Override
    protected void afterRotate(Node<T> g, Node<T> p, Node<T> child) {
        super.afterRotate(g, p, child);
        // 更新高度
        updateHeight(g);
        updateHeight(p);
    }

    @Override
    protected Node<T> createNode(T item, Node<T> parent) {
        return new AVLNode<>(item, parent);
    }

    /**
     * 节点是否平衡
     */
    private boolean isBalanced(Node<T> node) {
        return Math.abs(((AVLNode<T>) node).balanceFactor()) <= 1;
    }

    /**
     * 更新高度
     */
    private void updateHeight(Node<T> node) {
        ((AVLNode<T>) node).updateHeight();
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
            return left == null ? 0 : ((AVLNode<T>) left).height;
        }

        /**
         * 右子节点的高度
         */
        private int rightHeight() {
            return right == null ? 0 : ((AVLNode<T>) right).height;
        }

    }
}
