package ru.stoliarenkoas.gb.structures;

import com.sun.istack.internal.NotNull;

public class MyChainingHashMap<K, V> implements MyHashMap<K, V>{
   
    private Object[] array;
    private int size = 0;

    private class Node {
        K key;
        V value;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyChainingHashMap(int initSize) {
        if (initSize < 0) throw new IllegalArgumentException("Size cant be negative");
        if (initSize < 8) initSize = 8;
        this.array = new Object[initSize];
    }

    public MyChainingHashMap() {
        this(16);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getArrayPosition(@NotNull K key) {
        return key.hashCode() % array.length;
    }

    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    private Node getNode(K key) {
        if (key == null) throw new NullPointerException("key can not be null");
        int arrayPosition = getArrayPosition(key);
        Node node = (Node)array[arrayPosition];
        while (node != null && node.key != key) node = node.next;
        return node;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public V put(K key, V value) {
        if (value == null) throw new NullPointerException("value can not be null");
        final int arrayPosition = getArrayPosition(key);
        final Node oldNode = getNode(key);
        final V oldValue = oldNode == null ? null : oldNode.value;
        if (oldNode == null) {
            final Node newNode = new Node(key, value);
            newNode.next = (Node)array[arrayPosition];
            array[arrayPosition] = newNode;
            size++;
            if (((float)size / array.length) > 0.7f) {
                int newSize = array.length > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : array.length * 2;
                if (newSize == Integer.MAX_VALUE && array.length - size < 2) throw new IllegalStateException("Size limit reached");
                resize(newSize);
            }
        } else {
            oldNode.value = value;
        }
        return oldValue;
    }

    public V remove(K key) {
        final int arrayPosition = getArrayPosition(key);
        Node currentNode = (Node)array[arrayPosition];
        if (currentNode == null) return null;
        Node previousNode = null;

        while (currentNode != null && currentNode.key != key) {
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        if (currentNode == null) return null;
        if (previousNode == null) array[arrayPosition] = null;
        else {
            previousNode.next = currentNode.next;
        }

        final V oldValue = currentNode.value;
        if (oldValue != null) {
            size--;
            array[arrayPosition] = null;
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
            Node oldNode = (Node)oldArray[i];
            while (oldNode != null) {
                put(oldNode.key, oldNode.value);
                oldNode = oldNode.next;
            }
        }
    }

}
