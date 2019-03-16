package ru.stoliarenkoas.gb.structures;

import org.junit.Test;

import static org.junit.Assert.*;

public class MYTreeMapTest {

    @Test
    public void testNull() {
        MyTreeMap<Integer, Object> map = new MyTreeMap<Integer, Object>();
        boolean npeKey = false;
        boolean npeValue = false;
        try {
            map.put(null, new Object());
        } catch (NullPointerException npe) {
            npeKey = true;
        }
        try {
            map.put(3, null);
        } catch (NullPointerException npe) {
            npeValue = true;
        }
        assertTrue(npeKey);
        assertTrue(npeValue);
    }

    @Test
    public void sizeTest() {
        MyTreeMap<Integer, Object> map = new MyTreeMap<Integer, Object>();
        int counter = 0;
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
            assertEquals(++counter, map.getSize());
        }
        int size = counter;
        for (int i = 0; i < size; i++) {
            map.remove((i) % size);
            counter--;
            assertEquals(counter, map.getSize());
        }
    }

    @Test
    public void depthTest() {
        MyTreeMap<Integer, Object> map = new MyTreeMap<Integer, Object>();
        int counter = 1;
        map.put(100, 100);
        assertEquals(counter, map.getDepth());
        for (int i = 0; i < 100; i+=2) {
            map.put(98 - i, i);
            assertEquals(++counter, map.getDepth());
        }
        for (int i = 0; i < 100; i+=2) {
            map.put(102 + i, i);
            assertEquals(counter, map.getDepth());
        }
        for (int i = 0; i < 98; i+=2) {
            map.put(101 + i, i);
            map.put(99 - i, i);
            assertEquals(counter, map.getDepth());
        }
    }

    @Test
    public void putTest() {
        MyTreeMap<Integer, Object> map = new MyTreeMap<Integer, Object>();
        for (int i = -30; i < 30; i++) {
            map.put(i, i+1);
        }
        for (int i = 29; i > -31; i--) {
            assertEquals(i+1, map.get(i));
        }
    }

    @Test
    public void removeTest() {
        MyTreeMap<Integer, Object> map = new MyTreeMap<Integer, Object>();
        map.put(-3, 77);
        map.put(0, 66);
        map.put(3, 55);
        assertNull(map.remove(52));
        assertEquals(66, map.remove(0));
        assertEquals(77, map.remove(-3));
        assertEquals(55, map.remove(3));
        assertNull(map.remove(3));
    }

}
