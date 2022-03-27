package com.junpu.data.structure.tree;

import com.junpu.data.structure.tree.printer.BinaryTrees;

/**
 * @author junpu
 * @date 2022/3/24
 */
public class Main {
    public static void main(String[] args) {
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
