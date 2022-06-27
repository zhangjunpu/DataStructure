package com.junpu.data.structure.sort;

/**
 * 快排
 *
 * @author junpu
 * @date 2022/6/27
 */
public class QuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        sort(arr, 0, arr.length);
    }

    private static void sort(int[] arr, int begin, int end) {
        if (begin >= end) return;
        int mid = pivotIndex(arr, begin, end);
        sort(arr, begin, mid);
        sort(arr, mid + 1, end);
    }

    private static int pivotIndex(int[] arr, int begin, int end) {
        // 暂存轴点
        int pivot = arr[begin];
        end--;

        while (begin < end) {
            while (begin < end) {
                if (pivot < arr[end]) {
                    end--;
                } else {
                    arr[begin] = arr[end];
                    break;
                }
            }

            while (begin < end) {
                if (pivot > arr[begin]) {
                    begin++;
                } else {
                    arr[end] = arr[begin];
                    break;
                }
            }
        }

        // begin == end，轴点赋值
        arr[begin] = pivot;
        return begin;
    }

}
