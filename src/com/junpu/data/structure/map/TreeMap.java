package com.junpu.data.structure.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author junpu
 * @date 2022/4/11
 */
public class TreeMap<K, V> implements Map<K, V> {
    private static final boolean BLACK = true; // 黑
    private static final boolean RED = false; // 红

    private int size;
    private Node<K, V> root;
    private final Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

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

    @Override
    public V put(K key, V value) {
        KeyNotNullCheck(key);

        if (root == null) {
            root = new Node<>(key, value, null);
            size++;

            // 新添加节点之后的处理
            afterAdd(root);
            return null;
        }

        Node<K, V> parent, node = root;
        int cmp;
        do {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                return oldValue;
            }
        } while (node != null);

        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) parent.right = newNode;
        if (cmp < 0) parent.left = newNode;
        size++;

        afterAdd(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
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
    private V remove(Node<K, V> node) {
        if (node == null) return null;

        size--;

        V oldValue = node.value;

        // 如果节点的度为2
        if (node.left != null && node.right != null) {
            Node<K, V> s = successor(node); // 找到后继节点
            node.key = s.key;
            node.value = s.value;
            node = s;
        }

        // 能来到这里，说明 node 节点的度只能是 0 或 1
        Node<K, V> child = node.left != null ? node.left : node.right;
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
        return oldValue;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (Objects.equals(value, node.value)) return true;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        traversal(root, visitor);
    }

    private boolean traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor == null) return true;
        if (traversal(node.left, visitor)) return true;
        if (visitor.visit(node.key, node.value)) return true;
        return traversal(node.right, visitor);
    }

    /**
     * 根据 T 查找对应的结点
     */
    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) {
                return node;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
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
    private void afterAdd(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 新节点为根节点，或"上溢"到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 父节点为黑色，什么都不用做
        if (isBlack(parent)) return;

        // 能来到这，说明父节点是红色
        Node<K, V> uncle = parent.sibling();
        // 不管以下的哪种情况，祖父节点都要染红，所以直接提取到这里染红
        Node<K, V> grand = red(parent.parent);

        // 如果 uncle 节点是红色
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }

        // 来到这，说明 uncle 节点是黑色
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
    private void afterRemove(Node<K, V> node, Node<K, V> replacement) {
        // node 节点是红色
        if (isRed(node)) return;

        // 来到这，说明 node 节点是黑色的
        // 如果 node 节点有一个红色的子节点，将红色子节点直接染黑
        if (isRed(replacement)) {
            black(replacement);
            return;
        }

        // node 为根节点
        Node<K, V> parent = node.parent;
        if (parent == null) return;

        // 来到这，说明 node 节点是黑色叶子节点【B树下溢】
        boolean isLeft = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = isLeft ? parent.right : parent.left;

        // 来到这，说明兄弟节点是黑色
        if (isLeft) {
            // 兄弟节点为红色
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateLeft(parent);
                sibling = parent.right;
            }

            // 兄弟节点至少有一个红色子节点（相当于B树的跟兄弟节点借）
            if (isRed(sibling.left) || isRed(sibling.right)) {
                // RL 情况，要先旋转成 RR 情况
                if (sibling.right == null) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                // 统一处理 RR 情况
                color(sibling, colorOf(parent)); // sibling 继承 parent 的颜色
                black(parent);
                black(sibling.right);
                rotateLeft(parent);
            } else { // 兄弟节点没有一个红色子节点（相当于B树的下溢）
                boolean isParentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (isParentBlack) afterRemove(parent, null);
            }
        } else {
            // 兄弟节点为红色
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateRight(parent);
                sibling = parent.left;
            }

            // 兄弟节点至少有一个红色子节点（相当于B树的跟兄弟节点借）
            if (isRed(sibling.left) || isRed(sibling.right)) {
                // LR 情况，要先旋转成 LL 情况
                if (sibling.left == null) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                // 统一处理 LL 情况
                color(sibling, colorOf(parent)); // sibling 继承 parent 的颜色
                black(parent);
                black(sibling.right);
                rotateRight(parent);
            } else { // 兄弟节点没有一个红色子节点（相当于B树的下溢）
                boolean isParentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (isParentBlack) afterRemove(parent, null);
            }
        }
    }

    /**
     * 左旋
     */
    private void rotateLeft(Node<K, V> g) {
        Node<K, V> p = g.right;
        Node<K, V> child = p.left;

        g.right = child;
        p.left = g;

        afterRotate(g, p, child);
    }

    /**
     * 右旋
     */
    private void rotateRight(Node<K, V> g) {
        Node<K, V> p = g.left;
        Node<K, V> child = p.right;

        g.left = child;
        p.right = g;

        afterRotate(g, p, child);
    }

    /**
     * 旋转后
     */
    private void afterRotate(Node<K, V> g, Node<K, V> p, Node<K, V> child) {
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

    /**
     * 后继结点
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        Node<K, V> tmp;
        if (node.right != null) {
            tmp = node.right;
            while (tmp.left != null) {
                tmp = tmp.left;
            }
            return tmp;
        }

        if (node.parent != null) {
            tmp = node;
            while (tmp.parent != null && tmp == tmp.parent.right) {
                tmp = tmp.parent;
            }
            return tmp.parent;
        }
        return null;
    }

    /**
     * 获取节点的颜色
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 节点是否为红色
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 节点是否是黑色
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 节点染色染色
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return null;
        node.color = color;
        return node;
    }

    /**
     * 将节点染红
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 将节点染黑
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 检查 key 是否为空
     */
    private void KeyNotNullCheck(K key) {
        if (key == null) throw new IllegalArgumentException("Key must not be null.");
    }

    private int compare(K k1, K k2) {
        if (comparator != null) return comparator.compare(k1, k2);
        return ((Comparable<K>) k1).compareTo(k2);
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * 如果是左子树
         */
        private boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 如果是右子树
         */
        private boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 兄弟节点
         */
        private Node<K, V> sibling() {
            if (isLeftChild()) return parent.right;
            if (isRightChild()) return parent.left;
            return null;
        }

        @Override
        public String toString() {
            String str = color == RED ? "R_" : "";
            return str + super.toString();
        }
    }
}
