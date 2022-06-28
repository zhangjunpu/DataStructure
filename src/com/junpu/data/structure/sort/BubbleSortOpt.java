package com.junpu.data.structure.sort;

/**
 * 冒泡排序
 *
 * @author junpu
 * @date 2022/5/27
 */
public class BubbleSortOpt extends SortInt {
    /**
     * 冒泡排序，完全有序优化
     * 用 sorted 记录这一轮是否交换过，如果在某一轮没有交换过，说明数组已经完全有序
     */
    @Override
    public void sort() {
        for (int i = arr.length - 1; i > 0; i--) {
            boolean sorted = true;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

    @Override
    protected String getSortName() {
        return "冒泡排序，完全有序优化";
    }
}
