package ru.stoliarenkoas.gb.etc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PowTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {2, 8}, {2, -8}, {-2, 8}, {-2, -8}, //full even
                {3, 7}, {3, -7}, {-3, 7}, {-3, -7}, //full odd
                {6, 12}, {6, -12}, {-6, 12}, {-6, -12}, //mixed
                {120, 4}, {120, -4}, {-120, 4}, {-120, -4}, //big base
                {2.5, 7}, {2.5, -7}, {-2.5, 7}, {-2.5, -7}, //floating point
                {0, 45}, {45, 0} //zero, java.util.Math gives infinity if raising zero to negative power
            }
        );
    }

    private double number;
    private int power;
    private static  final double ACCURACY = 0.00001;

    public PowTest(double number, int power) {
        this.number = number;
        this.power = power;
    }

    @Test
    public void powTest() {
        double expected = Math.pow(number, power);
        double result = Pow.pow(number, power);
        System.out.printf("No recursion method:%nNumber: %f, Power: %d%nExpected: %f, Result: %f%n%n", number, power, expected, result);
        Assert.assertEquals(expected, result, ACCURACY);
    }

    @Test
    public void recPowTest() {
        double expected = Math.pow(number, power);
        double result = Pow.recPow(number, power);
        System.out.printf("Recursion method:%nNumber: %f, Power: %d%nExpected: %f, Result: %f%n%n", number, power, expected, result);
        Assert.assertEquals(expected, result, ACCURACY);
    }

}
