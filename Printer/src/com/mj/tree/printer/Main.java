package com.mj.tree.printer;

/**
 * @author junpu
 * @date 2023/1/11
 */
public class Main {
    public static void main(String[] args) {
        BinaryTrees.println(new BinaryTreeInfo() {
            @Override
            public Object root() {
                return "A";
            }

            @Override
            public Object left(Object node) {
                if (node == "A") return "B";
                return null;
            }

            @Override
            public Object right(Object node) {
                if (node == "A") return "C";
                return null;
            }

            @Override
            public Object string(Object node) {
                return node;
            }
        });
    }
}
