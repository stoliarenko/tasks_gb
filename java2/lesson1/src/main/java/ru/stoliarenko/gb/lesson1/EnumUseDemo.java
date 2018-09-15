package ru.stoliarenko.gb.lesson1;

public class EnumUseDemo {

	public static void main(String[] args) {
		System.out.println("Names and positions:");
		for (EnumDemo enumValue : EnumDemo.values()) {
			System.out.printf("-Name = %s, Position = %d. %n", 
					enumValue.getName(), enumValue.getPosition());
		}
		System.out.printf("%nVariables and switch operator: %n");
		EnumDemo vlue1 = EnumDemo.SECOND;
		EnumDemo vlue2 = EnumDemo.valueOf("SECOND");
		System.out.printf("-Does return same value? - %s %n", vlue1==vlue2);
		
		switch(vlue2) {
		case SECOND: {
				System.out.println("-Can be used as `switch` condition? - true");
				break;
			}
		default: System.out.println("-Can be used as `switch` condition? - false");
		}

	}

}
