package ru.stoliarenko.gb.lesson1;

import java.util.ArrayList;
import java.util.List;

public class InterfaceDemo {

	public static void main(String[] args) {
		//Add some different talkers to list
		InterfaceDemo demo = new InterfaceDemo();
		List<Talking> talkers = new ArrayList<>();
		talkers.add(demo.new Bird());
		talkers.add(demo.new Dog());
		talkers.add(demo.new Elephant());
		
		//Let them talk
		for (Talking talking : talkers) {
			talking.talk();
		}
	}
	
	class Bird implements Talking{

		@Override
		public void talk() {
			System.out.println("I'm a bird!");
			
		}
		
	}
	class Dog implements Talking{

		@Override
		public void talk() {
			System.out.println("I'm a dog!");
			
		}
		
	}
	class Elephant implements Talking{

		@Override
		public void talk() {
			System.out.println("I'm an elephant!");
			
		}
		
	}

}
