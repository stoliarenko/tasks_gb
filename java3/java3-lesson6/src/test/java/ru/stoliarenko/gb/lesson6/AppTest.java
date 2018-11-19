package ru.stoliarenko.gb.lesson6;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit test for simple App.
 */

@RunWith(Parameterized.class)
public class AppTest {
    
    @Parameters
    public static List<int[]> data() {
        return Arrays.asList(new int[][] {
            {1, 4, 2, 4, 5, 1, 3},
            {4, 4, 4, 4, 4, 4, 2},
            {1, 2, 2, 2, 2, 2, 7},
            {3, 4, 5, 2, 1, 1, 1}
        });
    }
    private int[] testArray;
    public AppTest(int ... array) {
        this.testArray = array;
    }
    
    /**
     * splitByLast4 method tests
     */
    @Test(expected = RuntimeException.class)
    public void no4Test() {
        final int[] array = {1, 3, 5, 7, 9};
        App.splitByLast4(array);
    }
    @Test(expected = RuntimeException.class)
    public void emptySplitTest() {
        final int[] array = {};
        App.splitByLast4(array);
    }
    @Test(expected = NullPointerException.class)
    public void nullSplitTest() {
        App.splitByLast4(null);
    }
    @Test
    public void all4Test() {
        int[] inputArray = {4, 4, 4, 4, 4, 4};
        int[] resultArray = App.splitByLast4(inputArray);
        assertTrue(0 == resultArray.length);
    }
    @Test
    public void arraySplitTest() {
        int[] inputArray = {4, 5, 4, 4, 4, 5};
        int[] resultArray = App.splitByLast4(inputArray);
        int[] expectedArray = {5};
        assertArrayEquals(expectedArray, resultArray);
    }
    
    /**
     * checkArrayOf1And4 method tests
     */
    @Test(expected = NullPointerException.class)
    public void nullCheckTest() {
        App.checkArrayOf1And4(null);
    }
    @Test
    public void emptyCheckTest() {
        final boolean result = App.checkArrayOf1And4(new int[]{});
        assertFalse(result);
    }
    @Test
    public void validArrayTest() {
        final boolean resunt = App.checkArrayOf1And4(new int[] {1, 1, 4, 1, 4, 4});
        assertTrue(resunt);
    }
    @Test
    public void invalidArrayTest() {
        final boolean result = App.checkArrayOf1And4(testArray);
        assertFalse(result);
    }

}
