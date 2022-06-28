package com.junpu.data.structure.sort;

/**
 * 快排
 *
 * @author junpu
 * @date 2022/6/27
 */
public class QuickSort extends SortInt {

    @Override
    public void sort() {
        sort(0, arr.length);
    }

    private void sort(int begin, int end) {
        // 状态机
        if (end - begin < 2) return;
        // 找出轴点下标
        int mid = pivotIndex(begin, end);
        // 递归
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private int pivotIndex(int begin, int end) {
        // 暂存轴点
        int pivot = arr[begin];
        end--;

        while (begin < end) {
            while (begin < end) {
                if (pivot < arr[end]) {
                    end--;
                } else {
                    arr[begin] = arr[end];
                    begin++;
                    break;
                }
            }

            while (begin < end) {
                if (pivot > arr[begin]) {
                    begin++;
                } else {
                    arr[end] = arr[begin];
                    end--;
                    break;
                }
            }
        }

        // begin == end，轴点赋值
        arr[begin] = pivot;
        return begin;
    }

    @Override
    protected String getSortName() {
        return "快速排序";
    }
}
