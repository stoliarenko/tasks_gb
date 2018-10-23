package ru.stoliarenko.gb.java3_lesson1;

import org.junit.Test;
import static org.junit.Assert.*;

public final class ArrayElementSwapperTest {

    @Test
    public void swapTest(){
        final String[] array = new String[3];
        final String firstElement = "first element";
        final String secondElement = "second element";
        final String thirdElement = "third element";
        array[0] = firstElement;
        array[1] = secondElement;
        array[2] = thirdElement;
        
        ArrayElementSwapper.swap(array);
        assertEquals(array[2], firstElement);
        assertEquals(array[0], thirdElement);
        assertEquals(array[1], secondElement);
    }
    
    @Test
    public void lowSizeTest() {
        final Object[] array = new Object[0];
        ArrayElementSwapper.swap(array);
    }
}
