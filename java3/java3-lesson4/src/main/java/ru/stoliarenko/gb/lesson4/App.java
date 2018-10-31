package ru.stoliarenko.gb.lesson4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class App {
    
    private class NumberValue{
        long value;
    }
    
    private class TaskFirst extends Thread{
        private final NumberValue numberValue;
        
        public TaskFirst(final NumberValue value) {this.numberValue = value;}
        
        @Override
        public void run() {System.out.println(numberValue.value);}
    }
    
    private class TaskSecond extends Thread{
        private final NumberValue numberValue;
        
        public TaskSecond(final NumberValue value) {this.numberValue = value;}
        
        @Override
        public void run() {numberValue.value = 1;}
    }
    
    @Test
    public void simpleTest() {
        final NumberValue numberValue = new NumberValue();
        final ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new TaskSecond(numberValue));
        executor.submit(new TaskFirst(numberValue));
        System.out.println("main: " + numberValue.value + "\n");
    }
    
    @Test
    public void complexTest() {
        for (int i = 0; i < 1000; i++) {simpleTest();}
    }
}
