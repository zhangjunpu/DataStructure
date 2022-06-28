package com.junpu.data.structure.sort;

/**
 * 插入排序
 *
 * @author junpu
 * @date 2022/5/27
 */
public class InsertionSort extends SortInt {
    @Override
    public void sort() {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] > arr[j - 1]) break;
                swap(j, j - 1);
            }
        }
    }

    @Override
    protected String getSortName() {
        return "插入排序";
    }
}
