package ru.stoliarenko.gb.lesson4;

import org.junit.Test;

public final class AbcTask {
    private String lastSymbol = null;
    
    private class TaskA extends Thread{
        private static final String SYMBOL = "A";
        
        @Override
        public void run() {
            for(int i = 0; i < 5; i++) {
                if(lastSymbol != null && lastSymbol != "C") {
                    try {
                        synchronized(AbcTask.this) {
                            AbcTask.this.wait();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted task A");
                    }
                }
                synchronized(AbcTask.this) {
                    System.out.println(SYMBOL);
                    lastSymbol = SYMBOL;
                    AbcTask.this.notifyAll();
                }
            }
        }
    }
    
    private class TaskB extends Thread{
        private static final String SYMBOL = "B";
        @Override
        public void run() {
            for(int i = 0; i < 5; i++) {
                if(lastSymbol != "A") {
                    try {
                        synchronized(AbcTask.this) {
                            AbcTask.this.wait();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted task B");
                    }
                }
                synchronized(AbcTask.this) {
                    System.out.println(SYMBOL);
                    lastSymbol = SYMBOL;
                    AbcTask.this.notifyAll();
                }
            }
        }
    }
    
    private class TaskC extends Thread{
        private static final String SYMBOL = "C";
        @Override
        public void run() {
            for(int i = 0; i < 5; i++) {
                if(lastSymbol != "B") {
                    try {
                        synchronized(AbcTask.this) {
                            AbcTask.this.wait();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted task C");
                    }
                }
                synchronized(AbcTask.this) {
                    System.out.println(SYMBOL);
                    lastSymbol = SYMBOL;
                    AbcTask.this.notifyAll();
                }
            }
        }
    }
    
    @Test
    public void test() {
        final Thread A = new Thread(this.new TaskA());
        final Thread B = new Thread(this.new TaskB());
        final Thread C = new Thread(this.new TaskC());
        
        A.setName("Thread-A");
        B.setName("Thread-B");
        C.setName("Thread-C");
        
        A.start();
        B.start();
        C.start();
        
        try {
            A.join();
            B.join();
            C.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted test thread");
        }
    }
    
}
