package ru.stoliarenkoas.gb.structures;

import org.junit.Test;
import static org.junit.Assert.*;

public class MyHashMapTest {

    @Test
    public void probingMapTest() {
        final int initialSize = 8;
        final MyHashMap<String, String> map = new MyProbingHashMap<String, String>(initialSize);
        final String[] strings = new String[initialSize * 2];

        //initialize keys
        for (int i = 0; i < strings.length; i++) {
            strings[i] = String.valueOf(i);
        }

        testMap(map, strings);
    }

    @Test
    public void chainingMapTest() {
        final int initialSize = 8;
        final MyHashMap<String, String> map = new MyChainingHashMap<String, String>(initialSize);
        final String[] strings = new String[initialSize * 2];

        //initialize keys
        for (int i = 0; i < strings.length; i++) {
            strings[i] = String.valueOf(i);
        }

        testMap(map, strings);
    }

    private void testMap(MyHashMap<String, String> map, String[] strings) {
        assertTrue(map.isEmpty());

        //fill map
        for (int i = 0; i < strings.length; i++) {
            assertEquals(i, map.size());
            assertFalse(map.contains(strings[i]));
            map.put(strings[i], String.valueOf(i*10));
            assertTrue(map.contains(strings[i]));
        }
        //check values
        for (int i = 0; i < strings.length; i++) {
            final String expected = String.valueOf(i*10);
            final String fromMap = map.get(strings[i]);
            assertEquals(expected, fromMap);
        }
        //replace values
        for (int i = 0; i < strings.length; i++) {
            assertEquals(strings.length, map.size());
            map.put(strings[i], String.valueOf(i*12));
        }
        //check values
        for (int i = 0; i < strings.length; i++) {
            final String expected = String.valueOf(i*12);
            final String fromMap = map.get(strings[i]);
            assertEquals(expected, fromMap);
        }
        //remove values
        for (int i = 0; i < strings.length; i++) {
            assertEquals(strings.length - i, map.size());
            assertTrue(map.contains(strings[i]));
            map.remove(strings[i]);
            assertFalse(map.contains(strings[i]));
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void negativeSizeTest() {
        MyHashMap<Integer, String> map = new MyProbingHashMap<Integer, String>(-8);
    }

}
