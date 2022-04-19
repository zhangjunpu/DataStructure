package com.junpu.data.structure.tree;

import java.util.Comparator;

/**
 * 红黑树
 *
 * @author junpu
 * @date 2022/4/2
 */
public class RedBlackTree<T> extends BalanceBinarySearchTree<T> {

    private static final int BLACK = 0; // 黑
    private static final int RED = 1; // 红

    public RedBlackTree() {
        this(null);
    }

    public RedBlackTree(Comparator<T> comparator) {
        super(comparator);
    }

    /**
     * 一、如果新节点为根节点，直接染黑；
     * 二、如果父节点为黑色，直接添加（灰色节点的四种情况）；
     * 三、如果父节点为红色，看新节点的叔父节点的颜色；
     * —— 1. 如果叔父节点为黑色（绿色节点的四种情况）：
     * ———— LL：祖父节点右旋，祖父节点染红，父节点染黑；
     * ———— RR：祖父节点左旋，祖父节点染红，父节点染黑；
     * ———— LR：父节点先左旋，祖父节点再右旋，自己染黑，祖父节点染红；
     * ———— RL：父节点先右旋，祖父节点再左旋，自己染黑，祖父节点染红；
     * —— 2. 如果叔父节点为红色（蓝色节点的四种情况）:
     * ———— 1> 将父节点和叔父节点都染成黑色，将祖父节点染成红色；
     * ———— 2> 再将祖父节点当成一个新节点，执行上边的操作；
     */
    @Override
    protected void afterAdd(Node<T> node) {
        Node<T> parent = node.parent;

        // 新节点为根节点，或"上溢"到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 父节点为黑色，什么都不用做
        if (isBlack(parent)) return;

        // 能来到这，说明父节点是红色，要看叔父节点的颜色
        Node<T> uncle = parent.sibling();
        // 不管以下的哪种情况，祖父节点都要染红，所以直接提取到这里染红
        Node<T> grand = red(parent.parent);

        // 如果叔父节点是红色，将父节点和叔父节点都染黑，然后将祖父节点当成新节点，递归
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }

        // 叔父节点是黑色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    /**
     * 一、node 为根节点，直接删除；
     * 二、node 节点是红色，直接删除；
     * 三、node 节点是黑色：
     * —— 1. 如果 node 节点有一个红色的子节点，将红色子节点直接染黑；
     * —— 2. 如果 node 节点是叶子节点：
     * ———— node 节点的兄弟节点为黑色：
     * —————— 1. 兄弟节点至少有一个红色子节点（相当于B树的跟兄弟节点借）：
     * ———————— LL：父节点右旋；
     * ———————— LR：兄弟节点左旋，父节点右旋；
     * ———————— RR：父节点左旋；
     * ———————— RL：兄弟节点右旋，父节点左旋；
     * ———————— 旋转后的中心节点继承父节点的颜色，左右两个子节点染黑；
     * —————— 2. 兄弟节点没有一个红色子节点（相当于B树的下溢）：
     * ———————— 父节点染黑，兄弟节点染红；
     * ———————— 如果父节点原来就是黑色，则将父节点当成删除节点，递归；
     * ———— node 节点的兄弟节点为红色：
     * —————— 1. 兄弟节点染黑，父节点染红，父节点旋转；
     * —————— 2. 之后回到了兄弟节点为黑色的情况；
     */
    @Override
    protected void afterRemove(Node<T> node, Node<T> replacement) {
        // node 节点是红色
        if (isRed(node)) return;

        // 来到这，说明 node 节点是黑色的
        // 如果 node 节点有一个红色的子节点，将红色子节点直接染黑
        if (isRed(replacement)) {
            black(replacement);
            return;
        }

        // node 为根节点
        Node<T> parent = node.parent;
        if (parent == null) return;

        // 来到这，说明删除的是黑色叶子节点【B树下溢】
        // 判断被删除的 node 是做还是右
        boolean isLeft = parent.left == null || node.isLeftChild();
        Node<T> sibling = isLeft ? parent.right : parent.left;
        if (isLeft) { // 被删除的节点是父节点的左子节点，兄弟节点在右边
            // 兄弟节点为红色，对父节点进行旋转，旋转后的父节点，继承了兄弟节点的黑色子节点，情况又回到了兄弟节点是黑色的情况
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateLeft(parent);
                sibling = parent.right; // 更换兄弟
            }

            // 兄弟节点没有一个红色子节点，父节点要向下跟兄弟节点合并（相当于B树的下溢）
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                boolean isParentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                // 如果父节点是黑色的，将父节点当成删除的节点，递归
                if (isParentBlack) afterRemove(parent, null);
            } else { // 兄弟节点至少有一个红色子节点，向兄弟节点借元素（相当于B树的跟兄弟节点借）
                // RL 情况，要先旋转成 RR 情况
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                // 统一处理 RR 情况
                color(sibling, colorOf(parent)); // sibling 继承 parent 的颜色
                black(parent);
                black(sibling.right);
                rotateLeft(parent);
            }
        } else { // 被删除的节点是父节点的右子节点，兄弟节点在左边
            // 兄弟节点为红色，旋转父节点，然后兄弟节点就变成了原先红色兄弟节点的黑色子节点，情况回归到黑色兄弟节点的情况
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateRight(parent);
                sibling = parent.left;
            }

            // 兄弟节点没有一个红色子节点，父节点要向下跟兄弟节点合并（相当于B树的下溢）
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                boolean isParentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (isParentBlack) afterRemove(parent, null);
            } else { // 兄弟节点至少有一个红色子节点，想兄弟借元素（相当于B树的跟兄弟节点借）
                // LR 情况，要先旋转成 LL 情况
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                // 统一处理 LL 情况
                color(sibling, colorOf(parent)); // sibling 继承 parent 的颜色
                black(parent);
                black(sibling.left);
                rotateRight(parent);
            }
        }
    }

    /**
     * 获取节点的颜色
     */
    private int colorOf(Node<T> node) {
        return node == null ? BLACK : ((RBNode<T>) node).color;
    }

    /**
     * 节点是否为红色
     */
    private boolean isRed(Node<T> node) {
        return colorOf(node) == RED;
    }

    /**
     * 节点是否是黑色
     */
    private boolean isBlack(Node<T> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 节点染色染色
     */
    private Node<T> color(Node<T> node, int color) {
        if (node == null) return null;
        ((RBNode<T>) node).color = color;
        return node;
    }

    /**
     * 将节点染红
     */
    private Node<T> red(Node<T> node) {
        return color(node, RED);
    }

    /**
     * 将节点染黑
     */
    private Node<T> black(Node<T> node) {
        return color(node, BLACK);
    }

    @Override
    protected Node<T> createNode(T item, Node<T> parent) {
        return new RBNode<>(item, parent);
    }

    private static class RBNode<T> extends Node<T> {
        int color = RED;

        public RBNode(T item, Node<T> parent) {
            super(item, parent);
        }

        @Override
        public String toString() {
            String str = color == RED ? "R_" : "";
            return str + super.toString();
        }
    }

}
