package ru.stoliarenko.gb.lesson1;

public enum EnumDemo {
	FIRST("first", 1),
	THIRD("third", 3),
	SECOND("second", 2);
	
	private String name;
	private int position;
	private EnumDemo(String name, int position) {
		this.name = name;
		this.position = position;
	}
	public int getPosition() {
		return position;
	}
	public String getName() {
		return name;
	}
	
}

