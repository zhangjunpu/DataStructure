package com.junpu.data.structure;

import com.junpu.data.structure.list.ArrayList;
import com.junpu.data.structure.list.LinkedList;
import com.junpu.data.structure.list.List;
import com.junpu.data.structure.list.SingleLinkedList;
import com.junpu.data.structure.map.HashMap;
import com.junpu.data.structure.map.Map;
import com.junpu.data.structure.map.TreeMap;
import com.junpu.data.structure.model.Key;
import com.junpu.data.structure.model.SubKey1;
import com.junpu.data.structure.model.SubKey2;
import com.junpu.data.structure.set.Set;
import com.junpu.data.structure.set.TreeSet;
import com.junpu.data.structure.tree.*;
import com.junpu.data.structure.tree.printer.BinaryTrees;
import com.junpu.data.structure.utils.Asserts;
import com.junpu.data.structure.utils.Times;
import com.junpu.data.structure.utils.file.FileInfo;
import com.junpu.data.structure.utils.file.Files;

/**
 * @author junpu
 * @date 2022/4/11
 */
public class Main {
    public static void main(String[] args) {
//        ListTest.linkedListTest();
//        TreeTest.rbTreeTest();
//        testSet();
//        MapTest.testTreeMap();
        MapTest.testHashMap();
//        TreeTest.test();
    }

    /**
     * Map 测试类
     *
     * @author junpu
     * @date 2022/4/18
     */
    private static class MapTest {

        private static void testHashMap() {
            test1();
            test2(new HashMap<>());
            test3(new HashMap<>());
            test4(new HashMap<>());
            test5(new HashMap<>());
        }

        static void test1Map(Map<String, Integer> map, String[] words) {
            Times.test(map.getClass().getName(), () -> {
                for (String word : words) {
                    Integer count = map.get(word);
                    count = count == null ? 0 : count;
                    map.put(word, count + 1);
                }
                System.out.println(map.size()); // 17188

                int count = 0;
                for (String word : words) {
                    Integer i = map.get(word);
                    count += i == null ? 0 : i;
                    map.remove(word);
                }
                Asserts.test(count == words.length);
                Asserts.test(map.size() == 0);
            });
        }

        static void test1() {
            String filepath = "D:\\WorkSpaces\\SDK\\java src\\java\\util\\concurrent";
            FileInfo fileInfo = Files.read(filepath, null);
            String[] words = fileInfo.words();

            System.out.println("总行数：" + fileInfo.getLines());
            System.out.println("单词总数：" + words.length);
            System.out.println("-------------------------------------");

            test1Map(new TreeMap<>(), words);
            test1Map(new HashMap<>(), words);
//            test1Map(new LinkedHashMap<>(), words);
        }

        static void test2(HashMap<Object, Integer> map) {
            for (int i = 1; i <= 20; i++) {
                map.put(new Key(i), i);
            }
            for (int i = 5; i <= 7; i++) {
                map.put(new Key(i), i + 5);
            }
            Asserts.test(map.size() == 20);
            Asserts.test(map.get(new Key(4)) == 4);
            Asserts.test(map.get(new Key(5)) == 10);
            Asserts.test(map.get(new Key(6)) == 11);
            Asserts.test(map.get(new Key(7)) == 12);
            Asserts.test(map.get(new Key(8)) == 8);
        }

        static void test3(HashMap<Object, Integer> map) {
            map.put(null, 1); // 1
            map.put(new Object(), 2); // 2
            map.put("jack", 3); // 3
            map.put(10, 4); // 4
            map.put(new Object(), 5); // 5
            map.put("jack", 6);
            map.put(10, 7);
            map.put(null, 8);
            map.put(10, null);
            Asserts.test(map.size() == 5);
            Asserts.test(map.get(null) == 8);
            Asserts.test(map.get("jack") == 6);
            Asserts.test(map.get(10) == null);
            Asserts.test(map.get(new Object()) == null);
            Asserts.test(map.containsKey(10));
            Asserts.test(map.containsKey(null));
            Asserts.test(map.containsValue(null));
            Asserts.test(map.containsValue(1) == false);
        }

        static void test4(HashMap<Object, Integer> map) {
            map.put("jack", 1);
            map.put("rose", 2);
            map.put("jim", 3);
            map.put("jake", 4);
            map.remove("jack");
            map.remove("jim");
            for (int i = 1; i <= 10; i++) {
                map.put("test" + i, i);
                map.put(new Key(i), i);
            }
            for (int i = 5; i <= 7; i++) {
                Asserts.test(map.remove(new Key(i)) == i);
            }
            for (int i = 1; i <= 3; i++) {
                map.put(new Key(i), i + 5);
            }
            Asserts.test(map.size() == 19);
            Asserts.test(map.get(new Key(1)) == 6);
            Asserts.test(map.get(new Key(2)) == 7);
            Asserts.test(map.get(new Key(3)) == 8);
            Asserts.test(map.get(new Key(4)) == 4);
            Asserts.test(map.get(new Key(5)) == null);
            Asserts.test(map.get(new Key(6)) == null);
            Asserts.test(map.get(new Key(7)) == null);
            Asserts.test(map.get(new Key(8)) == 8);
            map.traversal((key, value) -> {
                System.out.println(key + "_" + value);
                return false;
            });
        }

        static void test5(HashMap<Object, Integer> map) {
            for (int i = 1; i <= 20; i++) {
                map.put(new SubKey1(i), i);
            }
            map.put(new SubKey2(1), 5);
            Asserts.test(map.get(new SubKey1(1)) == 5);
            Asserts.test(map.get(new SubKey2(1)) == 5);
            Asserts.test(map.size() == 20);
        }

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

        static void test() {
            String filepath = "D:\\WorkSpaces\\SDK\\java src\\java\\util\\concurrent";
            FileInfo fileInfo = Files.read(filepath, null);
            String[] words = fileInfo.words();

            System.out.println("总行数：" + fileInfo.getLines());
            System.out.println("单词总数：" + words.length);
            System.out.println("-------------------------------------");

//            testTree(new AVLTree<>(), words);
            testTree(new RedBlackTree<>(), words);
        }

        static void testTree(Tree<String> tree, String[] words) {
            Times.test(tree.getClass().getName(), () -> {
                for (String word : words) {
                    tree.add(word);
                }
                System.out.println(tree.size()); // 17188

                for (String word : words) {
                    tree.remove(word);
                }
                Asserts.test(tree.size() == 0);
            });
        }

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


