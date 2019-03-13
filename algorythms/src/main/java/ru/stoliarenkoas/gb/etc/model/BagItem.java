package ru.stoliarenkoas.gb.etc.model;

public class BagItem {

    public static final BagItem SOCKS = new BagItem("Socks", 2, 1);
    public static final BagItem CANNED_FOOD = new BagItem("Canned food", 2, 3);
    public static final BagItem BASEBALL_BAT = new BagItem("Baseball bat", 5, 2);
    public static final BagItem MATCHES = new BagItem("Matches", 1, 5);
    public static final BagItem BALL = new BagItem("Ball", 4, 2);
    public static final BagItem GUITAR = new BagItem("Guitar", 7, 4);

    private String name;
    private int size;
    private int value;

    public BagItem(String name, int size, int value) {
        this.name = name;
        this.size = size;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }

}
