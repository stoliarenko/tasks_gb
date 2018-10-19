package ru.stoliarenko.gb.java3_lesson1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ru.stoliarenko.gb.java3_lesson1.fruitBox.Apple;
import ru.stoliarenko.gb.java3_lesson1.fruitBox.Box;
import ru.stoliarenko.gb.java3_lesson1.fruitBox.Fruit;
import ru.stoliarenko.gb.java3_lesson1.fruitBox.Orange;

public final class BoxTest {
    @Test
    public void weightTest() {
        final Box<Fruit> box = new Box<>();
        box.add(new Apple());
        assertTrue(box.getWeight() == 1f);
        box.add(new Orange());
        assertTrue(box.getWeight() == 2.5f);
        box.add(new Orange());
        assertTrue(box.getWeight() == 4f);
    }
    @Test
    public void compareTest() {
        final Box<Apple> box1 = new Box<>();
        final Box<Orange> box2 = new Box<>();
        
        box1.add(new Apple());
        box1.add(new Apple());
        box1.add(new Apple());
        
        box2.add(new Orange());
        box2.add(new Orange());
        
        assertTrue(box1.compare(box2));
        box2.add(new Orange());
        assertFalse(box1.compare(box2));
    }
    @Test
    public void pourTest() {
        final Box<Fruit> box1 = new Box<>();
        final Box<Fruit> box2 = new Box<>();
        
        box1.add(new Apple());
        box1.add(new Apple());
        box1.add(new Apple());
        assertTrue(box1.getWeight() == 3f);
        
        box2.add(new Orange());
        box2.add(new Orange());
        box2.add(new Orange());
        assertTrue(box2.getWeight() == 4.5f);
        
        box1.pour(box2);
        assertTrue(box1.getWeight() == 0f && box2.getWeight() == 7.5f);
    }
}
