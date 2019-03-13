package ru.stoliarenkoas.gb.structures;

public class MyStack<Element> {
    private MyDeQueue<Element> deque;

    public MyStack(int initCapacity) {
        deque = new MyDeQueue<Element>(initCapacity);
    }

    public MyStack() {
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

    public Element pop() {
        return deque.removeLast();
    }

    public Element peek() {
        return deque.getLast();
    }

}
