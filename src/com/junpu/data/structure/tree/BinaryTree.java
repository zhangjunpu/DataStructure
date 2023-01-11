package com.junpu.data.structure.tree;

import com.mj.tree.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树
 *
 * @author junpu
 * @date 2022/3/25
 */
public abstract class BinaryTree<T> implements Tree<T>, BinaryTreeInfo {
    protected int size;
    protected Node<T> root;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 前序遍历
     */
    public void preorder(Visitor<T> visitor) {
        if (visitor == null) return;
        preorder(root, visitor);
    }

    private boolean preorder(Node<T> node, Visitor<T> visitor) {
        if (node == null) return false;
        boolean isInterrupt = visitor.visit(node.item);
        if (!isInterrupt) isInterrupt = preorder(node.left, visitor);
        if (!isInterrupt) isInterrupt = preorder(node.right, visitor);
        return isInterrupt;
    }

    /**
     * 前序遍历 - 迭代
     */
    public void preorderTraversal(Visitor<T> visitor) {
        if (visitor == null) return;
        Stack<Node<T>> stack = new Stack<>();
        Node<T> node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                visitor.visit(node.item);
                stack.push(node);
                node = node.left;
            }
            Node<T> tmp = stack.pop();
            node = tmp.right;
        }
    }

    /**
     * 中序遍历
     */
    public void inorder(Visitor<T> visitor) {
        if (visitor == null) return;
        inorder(root, visitor);
    }

    private void inorder(Node<T> node, Visitor<T> visitor) {
        if (node == null) return;
        inorder(node.left, visitor);
        visitor.visit(node.item);
        inorder(node.right, visitor);
    }

    /**
     * 中序遍历 - 迭代
     */
    public void inorderTraversal(Visitor<T> visitor) {
        if (visitor == null) return;

        Stack<Node<T>> stack = new Stack<>();

        Node<T> node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            Node<T> tmp = stack.pop();
            visitor.visit(tmp.item);
            node = tmp.right;
        }
    }

    /**
     * 后序遍历
     */
    public void postorder(Visitor<T> visitor) {
        if (visitor == null) return;
        postorder(root, visitor);
    }

    private void postorder(Node<T> node, Visitor<T> visitor) {
        if (node == null) return;
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        visitor.visit(node.item);
    }

    /**
     * 后序遍历 - 迭代
     */
    public void postorderTraversal(Visitor<T> visitor) {
        if (visitor == null) return;
        Stack<Node<T>> stackReverse = new Stack<>();
        Stack<Node<T>> stack = new Stack<>();
        Node<T> node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stackReverse.push(node);
                stack.push(node);
                node = node.right;
            }
            Node<T> tmp = stack.pop();
            node = tmp.left;
        }

        while (!stackReverse.isEmpty()) {
            visitor.visit(stackReverse.pop().item);
        }
    }

    /**
     * 层序遍历
     */
    public void levelOrder(Visitor<T> visitor) {
        if (root == null || visitor == null) return;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            visitor.visit(node.item);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    /**
     * 计算高度、深度 - 递归
     */
    public int depth() {
        return depth(root);
    }

    private int depth(Node<T> node) {
        if (node == null) return 0;
        int leftDepth = depth(node.left);
        int rightDepth = depth(node.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 计算高度、深度 - 迭代
     */
    public int height() {
        int height = 0;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        int tmp = 1;
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            tmp--;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
            if (tmp == 0) {
                tmp = queue.size();
                height++;
            }
        }
        return height;
    }

    /**
     * 是否为完全二叉树
     */
    public boolean isCompleteBinaryTree() {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        boolean isLeaf = false;
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            if (isLeaf && (node.left != null || node.right != null)) return false;
            if (node.left != null)
                queue.offer(node.left);
            else if (node.right != null) // left == null && right != null
                return false;

            if (node.right != null)
                queue.offer(node.right);
            else // right == null && (left == null || left != null)
                isLeaf = true;
        }
        return true;
    }

    /**
     * 前驱结点
     */
    protected Node<T> predecessor(Node<T> node) {
        if (node == null) return null;

        Node<T> tmp = node.left;
        // 左子树不为空，前驱节点在左子树中，left.right.right...
        if (tmp != null) {
            while (tmp.right != null) {
                tmp = tmp.right;
            }
            return tmp;
        }

        while (node.isLeftChild()) {
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 后继结点
     */
    protected Node<T> successor(Node<T> node) {
        if (node == null) return null;
        Node<T> tmp = node.right;
        // 右子树不为空，后继节点在右子树中，right.left.left...
        if (tmp != null) {
            while (tmp.left != null) {
                tmp = tmp.left;
            }
            return tmp;
        }

        // 右子树为空，后继节点有可能在父节点、祖父节点中
        while (node.isRightChild()) {
            node = node.parent;
        }
        return node.parent;
    }

    protected static class Node<T> {
        T item;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        public Node(T item, Node<T> parent) {
            this.item = item;
            this.parent = parent;
        }

        /**
         * 如果是左子树
         */
        protected boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 如果是右子树
         */
        protected boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 兄弟节点
         */
        protected Node<T> sibling() {
            if (isLeftChild()) return parent.right;
            if (isRightChild()) return parent.left;
            return null;
        }

        @Override
        public String toString() {
            String parentStr = "null";
            if (parent != null) parentStr = parent.item.toString();
            return item + "_(" + parentStr + ")";
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<T>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<T>) node).right;
    }

    @Override
    public Object string(Object node) {
        return node.toString();
    }

}
