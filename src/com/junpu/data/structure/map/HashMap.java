package com.junpu.data.structure.map;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * HashMap
 *
 * @author junpu
 * @date 2022/4/17
 */
public class HashMap<K, V> implements Map<K, V> {
    private static final boolean BLACK = true; // 黑
    private static final boolean RED = false; // 红

    private int size = 0;
    private Node<K, V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f; // 装填因子

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
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
        if (size == 0) return;
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);

        // 取出 index 位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            table[index] = root = new Node<>(key, value, null);
            size++;
            // 新添加节点之后的处理
            afterAdd(root);
            return null;
        }

        // 添加新节点到红黑树上
        Node<K, V> parent, node = root;
        int cmp;
        K k1 = key;
        int h1 = hash(k1);
        boolean searched = false; // 是否已经扫描过这个 key
        do {
            parent = node;
            K k2 = node.key;
            int h2 = hash(k2);
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null &&
                    k1.getClass() == k2.getClass() &&
                    k1 instanceof Comparable &&
                    (cmp = ((Comparable<K>) k1).compareTo(k2)) != 0) {
            } else if (searched) { // key 已经扫描过，没必要再次扫描了
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else { // key 还没扫描过，先扫描，再比较内存地址
                Node<K, V> res;
                // 在 node 的左子树或右子树中扫描到了这个 key
                if ((node.left != null && (res = node(node.left, k1)) != null) ||
                        (node.right != null && (res = node(node.right, k1)) != null)) {
                    node = res;
                    cmp = 0;
                } else { // 没有扫描到 key，按内存地址比较
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                    searched = true;
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
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
        Node<K, V> n = node(key);
        return n == null ? null : n.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    private V remove(Node<K, V> node) {
        if (node == null) return null;

        size--;

        V oldValue = node.value;

        // 如果节点的度为2
        if (node.left != null && node.right != null) {
            Node<K, V> s = successor(node); // 找到后继节点
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            node = s;
        }

        // 能来到这里，说明 node 节点的度只能是 0 或 1
        Node<K, V> child = node.left != null ? node.left : node.right;
        if (child != null) { // child 不为空，说明节点的度为1
            child.parent = node.parent;
        }
        if (node.parent == null) { // 根节点
            table[index(node)] = child;
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
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> root : table) {
            if (root == null) continue;
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> n = queue.poll();
                if (Objects.equals(value, n.value)) return true;
                if (n.left != null) queue.offer(n.left);
                if (n.right != null) queue.offer(n.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> root : table) {
            if (root == null) continue;
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> n = queue.poll();
                if (visitor.visit(n.key, n.value)) return;
                if (n.left != null) queue.offer(n.left);
                if (n.right != null) queue.offer(n.right);
            }
        }
    }

    /**
     * 根据 T 查找对应的结点
     */
    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return node(root, key);
    }

    /**
     * 扫描
     */
    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = hash(k1);
        Node<K, V> result;
        while (node != null) {
            K k2 = node.key;
            int h2 = hash(k2);
            int cmp;
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k1 != null && k2 != null &&
                    k1.getClass() == k2.getClass() &&
                    k1 instanceof Comparable &&
                    (cmp = ((Comparable<K>) k1).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null && (result = node(node.right, k1)) != null) {
                return result;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private void afterAdd(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 新节点为根节点，或"上溢"到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 父节点为黑色，什么都不用做
        if (isBlack(parent)) return;

        // 能来到这，说明父节点是红色，要看叔父节点的颜色
        Node<K, V> uncle = parent.sibling();
        // 不管以下的哪种情况，祖父节点都要染红，所以直接提取到这里染红
        Node<K, V> grand = red(parent.parent);

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

        // 来到这，说明删除的是黑色叶子节点【B树下溢】
        // 判断被删除的 node 是做还是右
        boolean isLeft = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = isLeft ? parent.right : parent.left;
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
     * 左旋
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 旋转后
     */
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 更新 child.parent
        if (child != null) child.parent = grand;

        // 更新 p.parent
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            table[index(grand)] = parent;
        }
        parent.parent = grand.parent;
        grand.parent = parent;
    }

    /**
     * 后继结点
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        Node<K, V> tmp = node.right;
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
     * 被废弃
     */
    private int compare(K k1, K k2) {
        int h1 = hash(k1);
        int h2 = hash(k2);
        int cmp;
        if (h1 > h2) {
            return 1;
        } else if (h1 < h2) {
            return -1;
        } else if (Objects.equals(k1, k2)) {
            return 0;
        } else if (k1 != null && k2 != null &&
                k1.getClass() == k2.getClass() &&
                k1 instanceof Comparable &&
                (cmp = ((Comparable<K>) k1).compareTo(k2)) != 0) {
            return cmp;
        } else {
            String className1 = k1.getClass().getName();
            String className2 = k2.getClass().getName();
            cmp = className1.compareTo(className2);
            if (cmp != 0) return cmp;
        }
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    /**
     * 获取 key 的 hashCode
     */
    private static int hash(Object key) {
        int h = key == null ? 0 : key.hashCode();
        return h ^ (h >>> 16);
    }

    /**
     * 下标寻址，该方法等同于 hash(key) % table.length
     */
    private int index(K key) {
        return hash(key) & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return node.hash & (table.length - 1);
    }

    private static class Node<K, V> {
        K key;
        V value;
        int hash;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = hash(key);
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
