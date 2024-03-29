package com.junpu.data.structure.sort;

/**
 * 选择排序
 *
 * @author junpu
 * @date 2022/5/27
 */
public class SelectionSort extends SortInt {
    @Override
    public void sort() {
        for (int i = arr.length - 1; i > 0; i--) {
            int min = 0;
            for (int j = 1; j <= i; j++) {
                if (arr[j] > arr[min]) min = j;
            }
            swap(i, min);
        }
    }

    @Override
    protected String getSortName() {
        return "选择排序";
    }
}
