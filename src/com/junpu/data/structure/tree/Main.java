package com.junpu.data.structure.tree;

import com.junpu.data.structure.tree.printer.BinaryTrees;

/**
 * @author junpu
 * @date 2022/3/24
 */
public class Main {
    public static void main(String[] args) {
//        avlTreeTest();
        rbTreeTest();
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
        BinarySearchTree.Visitor<Integer> visitor = item -> {
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
