package com.junpu.data.structure.sort;

/**
 * 插入排序
 *
 * @author junpu
 * @date 2022/5/27
 */
public class InsertionSort {
    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] > arr[j - 1]) break;
                SortUtils.swap(arr, j, j - 1);
            }
        }
    }
}
