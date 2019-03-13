package ru.stoliarenkoas.gb.etc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RecursionTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {2, 8}, {2, -8},
                {3, 3}, {3, -3},
                {6, 2}, {6, -2},
                {5, 8}, {5, -8},
                {12, 4}, {12, -4},
                {0, 45}, {45, 0}
            }
        );
    }

    private int number, power;
    private static  final double ACCURACY = 0.000001;

    public RecursionTest(int number, int power) {
        this.number = number;
        this.power = power;
    }

    @Test
    public void powTest() {
        double expected = Math.pow(number, power);
        double result = Recursion.pow(number, power);
        System.out.printf("Number: %d, Power: %d%nExpected: %f, Result: %f%n%n", number, power, expected, result);
        Assert.assertEquals(expected, result, ACCURACY);
    }

    @Test
    public void recPowTest() {
        double expected = Math.pow(number, power);
        double result = Recursion.recPow(number, power);
        System.out.printf("Number: %d, Power: %d%nExpected: %f, Result: %f%n%n", number, power, expected, result);
        Assert.assertEquals(expected, result, ACCURACY);
    }

}
