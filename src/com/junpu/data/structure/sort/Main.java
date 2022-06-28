package com.junpu.data.structure.sort;

/**
 * @author junpu
 * @date 2022/5/27
 */
public class Main {
    public static void main(String[] args) {

        SortInt[] sorts = {
//                new BubbleSort(),
//                new BubbleSortOpt(),
                new BubbleSortOpt2(),
                new SelectionSort(),
                new InsertionSort(),
                new QuickSort()
        };

        for (SortInt sort : sorts) {
            int[] arr = new int[]{5, 3, 9, 6, 1, 8, 6, 2, 7, 4, 10, 11, 12};
            sort.sort(arr);
            System.out.println(sort);
        }
    }
}
