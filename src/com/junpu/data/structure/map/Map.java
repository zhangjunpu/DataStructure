package com.junpu.data.structure.map;

/**
 * Map
 *
 * @author junpu
 * @date 2022/4/11
 */
public interface Map<K, V> {
    int size();
    boolean isEmpty();
    void clear();
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean containsKey(K key);
    boolean containsValue(V value);
    void traversal(Visitor<K, V> visitor);
}
