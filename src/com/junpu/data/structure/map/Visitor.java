package com.junpu.data.structure.map;

/**
 * @author junpu
 * @date 2022/4/11
 */
public interface Visitor<K, V> {
    boolean visit(K key, V value);
}
