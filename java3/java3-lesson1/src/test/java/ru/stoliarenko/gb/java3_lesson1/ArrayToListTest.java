package ru.stoliarenko.gb.java3_lesson1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import java.util.ArrayList;

public final class ArrayToListTest {
    @Test
    public void contentTest() {
        final String[] array = new String[3];
        final String firstElement = "first element";
        final String secondElement = "second element";
        final String thirdElement = "third element";
        array[0] = firstElement;
        array[1] = secondElement;
        array[2] = thirdElement;
        
        final ArrayList<String> list = ArrayToList.toArrayList(array);
        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], list.get(i));
        }
    }
    @Test
    public void nullTest() {
        final Object result;
        result = ArrayToList.toArrayList(null);
        assertNull(result);
    }
}
