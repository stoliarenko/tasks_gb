package ru.stoliarenko.gb.lesson1;

public class InnerClassDemo {
	public static void main(String[] args) {
		InnerClassDemo outer = new InnerClassDemo(87);
		InnerClassDemo.Inner inner = outer.new Inner(37);
		
		System.out.println();
		
		inner.innerTest();
		outer.outerTest();
	}
	class Inner {
        private int innerVar;
        public Inner(int innerVar) {
            this.innerVar = innerVar;
        }
        void innerTest() {
            System.out.println("innerVar: " + innerVar);
            System.out.println("outerVar: " + outerVar);
        }
    }
    private int outerVar;
    public InnerClassDemo(int outerVar) {
        this.outerVar = outerVar;
    } 
    public void outerTest() {
        System.out.println("outerVar: " + outerVar);
        // System.out.println("innerVar: " + innerVar); тут ошибка
        Inner io = new Inner(20);
        System.out.println("io.innerVar = " + io.innerVar);
    }

}
