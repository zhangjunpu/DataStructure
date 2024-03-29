package com.junpu.data.structure.sort;

/**
 * 冒泡排序
 *
 * @author junpu
 * @date 2022/5/27
 */
public class BubbleSort extends SortInt {
    /**
     * 冒泡排序
     */
    @Override
    public void sort() {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
    }

    @Override
    protected String getSortName() {
        return "冒泡排序";
    }
}
