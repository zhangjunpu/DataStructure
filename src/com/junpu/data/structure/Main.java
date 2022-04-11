package com.junpu.data.structure;

import com.junpu.data.structure.list.ArrayList;
import com.junpu.data.structure.list.LinkedList;
import com.junpu.data.structure.list.List;
import com.junpu.data.structure.list.SingleLinkedList;
import com.junpu.data.structure.map.Map;
import com.junpu.data.structure.map.TreeMap;
import com.junpu.data.structure.set.Set;
import com.junpu.data.structure.set.TreeSet;
import com.junpu.data.structure.tree.AVLTree;
import com.junpu.data.structure.tree.BinarySearchTree;
import com.junpu.data.structure.tree.RedBlackTree;
import com.junpu.data.structure.tree.Visitor;
import com.junpu.data.structure.tree.printer.BinaryTrees;

/**
 * @author junpu
 * @date 2022/4/11
 */
public class Main {
    public static void main(String[] args) {
//        ListTest.linkedListTest();
//        TreeTest.rbTreeTest();
//        testSet();
        testMap();
    }

    private static void testMap() {
        Map<Object, Integer> map = new TreeMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(2, 4);
        System.out.println(map.size());
    }

    private static void testSet() {
        Set<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(2);
        System.out.println(set);
        set.remove(4);
        set.remove(3);
        System.out.println(set);
        set.traversal(item -> {
            System.out.println(item);
            return false;
        });

        System.out.println(set.contains(2));
    }

    /**
     * Tree Test
     *
     * @author junpu
     * @date 2022/4/11
     */
    private static class TreeTest {
        private static void rbTreeTest() {
            int[] arr = new int[]{77, 97, 40, 26, 34, 39, 14, 62, 82, 6, 96, 84, 72, 66, 80};
            RedBlackTree<Integer> rb = new RedBlackTree<>();
            for (int i : arr) {
                rb.add(i);
//            System.out.println("add " + i);
//            BinaryTrees.println(rb);
//            System.out.println("----------------------");
            }
            BinaryTrees.println(rb);
            System.out.println("----------------------");

            for (int i : arr) {
                rb.remove(i);
                System.out.println("remove " + i);
                BinaryTrees.println(rb);
                System.out.println("----------------------");
            }

        }

        private static void avlTreeTest() {
            int[] arr = new int[]{7, 3, 5, 9, 4, 8, 2, 11, 6, 1, 10};
            AVLTree<Integer> avl = new AVLTree<>();
            for (int i : arr) {
                avl.add(i);
//            System.out.println("add " + i);
//            BinaryTrees.println(avl);
//            System.out.println("----------------------");
            }
            BinaryTrees.println(avl);
            System.out.println("----------------------");

            avl.remove(6);
            System.out.println("---------------------- remove " + 6);
            BinaryTrees.println(avl);

            avl.remove(7);
            System.out.println("---------------------- remove " + 7);
            BinaryTrees.println(avl);

            avl.remove(5);
            System.out.println("---------------------- remove " + 5);
            BinaryTrees.println(avl);

            avl.remove(9);
            System.out.println("---------------------- remove " + 9);
            BinaryTrees.println(avl);

            avl.remove(11);
            System.out.println("---------------------- remove " + 11);
            BinaryTrees.println(avl);
        }

        private static void bstTest() {
            //        int[] arr = new int[]{7, 3, 5, 9, 4, 8, 2, 11, 6, 1, 10};
            int[] arr = new int[]{7, 2, 3, 1, 8};
            BinarySearchTree<Integer> bst = new BinarySearchTree<>();
            for (int i : arr) {
                bst.add(i);
            }
            BinaryTrees.println(bst);
            System.out.println("height: " + bst.height());
            System.out.println("isCompleteBinaryTree: " + bst.isCompleteBinaryTree());
//        bstTraversal(bst);
        }

        /**
         * 遍历
         */
        private static void bstTraversal(BinarySearchTree<Integer> bst) {
            Visitor<Integer> visitor = item -> {
                System.out.print(item + " ");
                return item == 9;
            };

            System.out.println("前序遍历：");
            // 前序遍历
            bst.preorder(visitor);
            System.out.println();
            bst.preorderTraversal(visitor);
            System.out.println();

            // 中序遍历
            System.out.println("中序遍历：");
            bst.inorder(visitor);
            System.out.println();
            bst.inorderTraversal(visitor);
            System.out.println();

            // 后序遍历
            System.out.println("后序遍历：");
            bst.postorder(visitor);
            System.out.println();
            bst.postorderTraversal(visitor);
            System.out.println();

            // 层序遍历
            System.out.println("层序遍历：");
            bst.levelOrder(visitor);
            System.out.println();
        }
    }

    /**
     * List Test
     *
     * @author junpu
     * @date 2022/4/11
     */
    private static class ListTest {

        private static void linkedListTest() {
            List<Integer> list = new LinkedList<>();
//        testList(list);

            list.add(1);
            list.add(2);
            list.add(3);
            System.out.println(list);
            list.add(1, 10);
            System.out.println(list);
            list.set(0, 8);
            System.out.println(list);
            list.remove(3);
            System.out.println(list);
            System.out.println(list.get(1));
            System.out.println(list.indexOf(11));
        }

        private static void singleLinkedListTest() {
            List<Integer> list = new SingleLinkedList<>();
//        testList(list);

            list.add(1);
            list.add(2);
            list.add(3);
            list.add(1, 10);
            list.set(0, 8);
            list.remove(3);
            System.out.println(list);
            System.out.println(list.get(1));
            System.out.println(list.indexOf(11));

        }

        private static void arrayListTest() {
            List<Integer> list = new ArrayList<>();
            testList(list);
        }

        private static void testList(List<Integer> list) {
            for (int i = 0; i < 20; i++) {
                list.add(i + 1);
                System.out.println("add " + (i + 1));
            }
            System.out.println(list);
            for (int i = 0; i < 20; i++) {
                list.remove(0);
                System.out.println("remove " + (i + 1));
            }
            System.out.println(list);
        }
    }

}


