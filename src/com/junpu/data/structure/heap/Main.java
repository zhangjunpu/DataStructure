package com.junpu.data.structure.heap;

import com.mj.tree.printer.BinaryTreeInfo;
import com.mj.tree.printer.BinaryTrees;

/**
 * @author junpu
 * @date 2022/6/24
 */
public class Main {
    public static void main(String[] args) {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        BinaryTrees.println(heap);
        // get
        System.out.println(heap.get());
        // remove
        System.out.println(heap.remove());
        BinaryTrees.println(heap);
        // replace
        System.out.println(heap.replace(32));
        BinaryTrees.println(heap);
    }
}
