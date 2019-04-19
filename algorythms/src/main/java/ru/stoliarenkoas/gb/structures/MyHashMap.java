package ru.stoliarenkoas.gb.structures;

public interface MyHashMap<K, V> {
    int size();

    boolean isEmpty();

    V get(K key);

    boolean contains(K key);

    V put(K key, V value);

    V remove(K key);
}
