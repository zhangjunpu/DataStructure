package com.junpu.data.structure.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author junpu
 * @date 2022/4/6
 */
public class Main {
    public static void main(String[] args) {
        float a = 0.1f;
        System.out.println(Float.floatToIntBits(a));
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(a)));
        System.out.println(0b00111101110011001100110011001101);

//        Person p1 = new Person(18, 1.78f, "Jack");
//        Person p2 = new Person(18, 1.78f, "Jack");
//
//        Map<Object, Object> map = new HashMap<>();
//        map.put(p1, "123");
//        map.put(p2, "456");
//        System.out.println(map.size());
//
//        System.out.println(0b10000);
//        System.out.println(0b101);
    }


}
