package ru.stoliarenkoas.gb.structures;

import com.sun.istack.internal.NotNull;

@SuppressWarnings("unchecked")
public class MyProbingHashMap<K, V> implements MyHashMap<K, V> {
    private Object[] array;
    private int size = 0;

    private class Node {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyProbingHashMap(int initSize) {
        if (initSize < 0) throw new IllegalArgumentException("Size cant be negative");
        if (initSize < 8) initSize = 8;
        this.array = new Object[initSize];
    }

    public MyProbingHashMap() {
        this(16);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getDelta(K key) {
        return (key.hashCode() % (array.length / 7)) + 1;
    }

    private int getArrayPosition(@NotNull K key) {
        int arrayPosition = key.hashCode() % array.length;
        Node node = (Node)array[arrayPosition];
        while (node != null && !node.key.equals(key)) {
            arrayPosition = (arrayPosition + getDelta(key)) % array.length;
            node = (Node)array[arrayPosition];
        }
        return arrayPosition;
    }

    public V get(K key) {
        if (key == null) throw new NullPointerException("key can not be null");
        int arrayPosition = getArrayPosition(key);
        Node node = (Node)array[arrayPosition];

        return node == null ? null : node.value;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public V put(K key, V value) {
        if (value == null) throw new NullPointerException("value can not be null");
        final int arrayPosition = getArrayPosition(key);
        final V oldValue = array[arrayPosition] == null ? null : ((Node)array[arrayPosition]).value;
        array[arrayPosition] = new Node(key, value);
        if (oldValue == null) size++;
        if (((float)size / array.length) > 0.7f) {
            int newSize = array.length > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : array.length * 2;
            if (newSize == Integer.MAX_VALUE && array.length - size < 2) throw new IllegalStateException("Size limit reached");
            resize(newSize);
        }
        return oldValue;
    }

    public V remove(K key) {
        int arrayPosition = getArrayPosition(key);
        Node removable = (Node)array[arrayPosition];
        final V oldValue = removable == null ? null : removable.value;
        if (oldValue != null) {
            size--;
            Node lastForThisKey;
            Node current = null;
            K currentKey;
            int currentArrayPosition = arrayPosition;
            do {
                lastForThisKey = current;
                currentArrayPosition = (currentArrayPosition + getDelta(key)) % array.length;
                current = (Node)array[currentArrayPosition];
                currentKey = current ==  null ? null: current.key;
            } while (key.equals(currentKey));
            if (lastForThisKey != null) array[(array.length + currentArrayPosition - getDelta(key)) % array.length] = null;
            array[arrayPosition] = lastForThisKey;
            if (size > 8 && ((float)size / array.length) < 0.2f) resize(array.length / 2);
        }
        return oldValue;
    }

    private void resize(int newArraySize) {
        if (newArraySize < 0) throw new IllegalArgumentException("negative array size");
        Object[] oldArray = array;
        array = new Object[newArraySize];
        size = 0;

        for (int i = 0; i < oldArray.length; i++) {
            final Node oldNode = (Node)oldArray[i];
            if (oldNode == null) continue;
            put(oldNode.key, oldNode.value);
        }
    }

}
