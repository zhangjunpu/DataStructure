package com.junpu.data.structure.sort;

import java.util.Arrays;

/**
 * @author junpu
 * @date 2022/5/27
 */
public class Main {
    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 9, 6, 1, 8, 2, 7, 4, 10, 11, 12};
//        BubbleSort.sort(arr);
//        SelectionSort.sort(arr);
        InsertionSort.sort(arr);

//        BubbleSort.sortOpt(arr);
        System.out.println(Arrays.toString(arr));
    }
}
