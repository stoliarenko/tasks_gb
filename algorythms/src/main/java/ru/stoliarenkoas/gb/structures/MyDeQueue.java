package ru.stoliarenkoas.gb.structures;

public class MyDeQueue<Element> {

    //region fields
    private Object[] elements;

    private int size = 0;
    private int first = 0;
    private int last = 0; //please do not use
    //endregion

    //region constructors
    public MyDeQueue(int initSize){
        if (initSize < 1) elements = new Object[10];
        else elements = new Object[initSize];
    }

    public MyDeQueue() {
        this(10);
    }
    //endregion

    //region size methods
    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    private boolean resize(int newLength) {
        if (newLength == elements.length || newLength < 0) return false;
        Object[] newArray = new Object[newLength];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[(first + i) % elements.length];
        }
        elements = newArray;
        first = 0;
        return true;
    }

    private boolean grow() {
        int newSize = (int)(elements.length * 1.5 + 1);
        if (newSize < 1) newSize = Integer.MAX_VALUE;
        return resize(newSize);
    }
    //endregion

    //region last element methods
    public boolean addLast(Element e) {
        if (size == elements.length && !grow()) return false;

        elements[(first+size)%elements.length] = e;
        size++;
        return true;
    }

    public Element removeLast() {
        if (isEmpty()) return null;
        final Element deleted = (Element) elements[(first + --size) % elements.length];
        elements[(first + size) % elements.length] = null;

        if (size < elements.length / 4) resize(elements.length / 2 + 1);
        return deleted;
    }

    public Element getLast() {
        if (isEmpty()) return null;
        return (Element) elements[(first + size - 1) % elements.length];
    }
    //endregion

    //region first element methods
    public boolean addFirst(Element e) {
        if (size == elements.length && !grow()) return false;

        first = first == 0 ? elements.length - 1 : first - 1;
        elements[first] = e;
        size++;
        return true;
    }

    public Element getFirst() {
        if (isEmpty()) return null;
        return (Element) elements[first];
    }

    public Element removeFirst() {
        if (isEmpty()) return null;
        Element deleted = (Element) elements[first];
        first++;
        first %= elements.length;

        if (--size < elements.length / 4) resize(elements.length / 2 + 1);
        return deleted;
    }
    //endregion

}
