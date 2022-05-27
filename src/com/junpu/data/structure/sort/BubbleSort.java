package com.junpu.data.structure.sort;

/**
 * 冒泡排序
 *
 * @author junpu
 * @date 2022/5/27
 */
public class BubbleSort {
    /**
     * 冒泡排序
     */
    public static void sort(int[] arr) {
        int last = arr.length - 1;
        for (int i = last; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    SortUtils.swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 尾部局部有序优化 + 完全有序优化
     */
    public static void sortOpt(int[] arr) {
        int last = arr.length - 1;
        for (int i = last; i > 0; i--) {
            int endIndex = 0;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    SortUtils.swap(arr, j, j + 1);
                    endIndex = j + 1;
                }
            }
            i = endIndex;
        }
    }

    /**
     * 完全有序优化
     */
    public static void sortOpt1(int[] arr) {
        int last = arr.length - 1;
        for (int i = last; i > 0; i--) {
            boolean sorted = true;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    SortUtils.swap(arr, j, j + 1);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

}
