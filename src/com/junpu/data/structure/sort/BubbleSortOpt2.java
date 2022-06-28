package com.junpu.data.structure.sort;

/**
 * 冒泡排序
 *
 * @author junpu
 * @date 2022/5/27
 */
public class BubbleSortOpt2 extends SortInt {
    /**
     * 冒泡排序，尾部局部有序优化 + 完全有序优化
     * 用 endIndex 记录最后交换的位置下标，如果某一轮这个下标没在数组最后，说明这个下标后边的位置都是已经排好序的
     */
    @Override
    public void sort() {
        for (int i = arr.length - 1; i > 0; i--) {
            int endIndex = 0;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                    endIndex = j + 1;
                }
            }
            i = endIndex;
        }
    }

    @Override
    protected String getSortName() {
        return "冒泡排序，尾部局部有序优化 + 完全有序优化";
    }
}
