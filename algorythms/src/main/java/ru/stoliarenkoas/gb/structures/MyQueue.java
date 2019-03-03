package ru.stoliarenkoas.gb.structures;

public class MyQueue<Element> {
    private MyDeQueue<Element> deque;

    public MyQueue(int initCapacity) {
        deque = new MyDeQueue<Element>(initCapacity);
    }

    public MyQueue() {
        deque = new MyDeQueue<Element>();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public int getSize() {
        return deque.getSize();
    }

    public boolean push(Element e) {
        return deque.addLast(e);
    }

    public Element peek() {
        return deque.getFirst();
    }

    public Element pop() {
        return deque.removeFirst();
    }
}
