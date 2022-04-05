package com.junpu.data.structure.list;

/**
 * @author junpu
 * @date 2022/4/5
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + 1);
            System.out.println("add " + (i + 1));
        }
        System.out.println(list);
        for (int i = 0; i < 20; i++) {
            list.remove(0);
            System.out.println("remove " + (i + 1));
        }
        System.out.println(list);
    }
}
