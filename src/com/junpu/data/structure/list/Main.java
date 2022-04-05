package com.junpu.data.structure.list;

/**
 * @author junpu
 * @date 2022/4/5
 */
public class Main {
    public static void main(String[] args) {
//        arrayListTest();
        singleLinkedListTest();
    }

    private static void singleLinkedListTest() {
        List<Integer> list = new SingleLinkedList<>();
//        testList(list);

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1, 10);
        list.set(0, 8);
        list.remove(3);
        System.out.println(list);
        System.out.println(list.get(1));
        System.out.println(list.indexOf(11));

    }

    private static void arrayListTest() {
        List<Integer> list = new ArrayList<>();
        testList(list);
    }

    private static void testList(List<Integer> list) {
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
