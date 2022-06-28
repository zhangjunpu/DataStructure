package com.junpu.data.structure.sort;

import java.util.Arrays;

/**
 * @author junpu
 * @date 2022/6/28
 */
public abstract class SortInt {
    protected int[] arr;
    private int swapCount;

    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        this.arr = arr;
        sort();
    }

    protected abstract void sort();

    protected void swap(int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
        swapCount++;
    }

    protected String getSortName() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getSortName() + "\n" +
                Arrays.toString(arr) + ", swapCount=" + swapCount + "\n" +
                "-----------------------------------";
    }
}
