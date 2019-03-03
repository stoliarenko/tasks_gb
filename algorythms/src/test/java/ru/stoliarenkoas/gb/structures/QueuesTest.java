package ru.stoliarenkoas.gb.structures;

import static org.junit.Assert.*;
import org.junit.Test;

public class QueuesTest {

    @Test
    public void testLastElementOperations() {
        MyDeQueue<Integer> ints = new MyDeQueue<Integer>(1);
        assertEquals(ints.getSize(), 0);
        for (int i = 1; i < 5; i++) {
            ints.addLast(i);
            assertEquals(ints.getSize(), i);
            assertEquals((int)ints.getLast(), i);
        }
        assertEquals(ints.getSize(), 4);
        assertEquals(ints.removeLast(), Integer.valueOf(4));
        assertEquals(ints.removeLast(), Integer.valueOf(3));
        assertEquals(ints.removeLast(), Integer.valueOf(2));
        assertEquals(ints.removeLast(), Integer.valueOf(1));
        assertEquals(ints.getSize(), 0);
        assertNull(ints.removeLast());
        assertNull(ints.getLast());
        assertTrue(ints.isEmpty());

    }
    
    @Test
    public void testFirstElementOperations() {
        MyDeQueue<Integer> ints = new MyDeQueue<Integer>(1);
        assertEquals(ints.getSize(), 0);
        for (int i = 1; i < 5; i++) {
            ints.addFirst(i);
            assertEquals(ints.getSize(), i);
            assertEquals((int)ints.getFirst(), i);
        }
        assertEquals(ints.getSize(), 4);
        assertEquals(ints.removeFirst(), Integer.valueOf(4));
        assertEquals(ints.removeFirst(), Integer.valueOf(3));
        assertEquals(ints.removeFirst(), Integer.valueOf(2));
        assertEquals(ints.removeFirst(), Integer.valueOf(1));
        assertEquals(ints.getSize(), 0);
        assertNull(ints.removeFirst());
        assertNull(ints.getFirst());
        assertTrue(ints.isEmpty());

    }
    
    @Test
    public void queueTest() {
        MyQueue<Integer> ints = new MyQueue<Integer>(1);
        assertEquals(ints.getSize(), 0);
        for (int i = 1; i < 5; i++) {
            ints.push(i);
            assertEquals(ints.getSize(), i);
            assertEquals(ints.peek(), Integer.valueOf(1));
        }
        assertEquals(ints.getSize(), 4);
        assertEquals(ints.pop(), Integer.valueOf(1));
        assertEquals(ints.pop(), Integer.valueOf(2));
        assertEquals(ints.pop(), Integer.valueOf(3));
        assertEquals(ints.pop(), Integer.valueOf(4));
        assertEquals(ints.getSize(), 0);
        assertNull(ints.pop());
        assertNull(ints.peek());
        assertTrue(ints.isEmpty());

    }

    @Test
    public void stackTest() {
        MyStack<Integer> ints = new MyStack<Integer>(1);
        assertEquals(ints.getSize(), 0);
        for (int i = 1; i < 5; i++) {
            ints.push(i);
            assertEquals(ints.getSize(), i);
            assertEquals(ints.peek(), Integer.valueOf(i));
        }
        assertEquals(ints.getSize(), 4);
        assertEquals(ints.pop(), Integer.valueOf(4));
        assertEquals(ints.pop(), Integer.valueOf(3));
        assertEquals(ints.pop(), Integer.valueOf(2));
        assertEquals(ints.pop(), Integer.valueOf(1));
        assertEquals(ints.getSize(), 0);
        assertNull(ints.pop());
        assertNull(ints.peek());
        assertTrue(ints.isEmpty());
    }

}
