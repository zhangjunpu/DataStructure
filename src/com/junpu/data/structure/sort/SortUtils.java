package com.junpu.data.structure.sort;

/**
 * @author junpu
 * @date 2022/5/27
 */
public class SortUtils {
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
