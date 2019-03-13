package ru.stoliarenkoas.gb.etc;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReverseStringOrderTest {

    @Test
    public void testEmpty() {
        final String original = "";
        final String expected = "";

        final String reversed = ReverseStringOrder.reverse(original);
        assertEquals(expected, reversed);
    }

    @Test
    public void testSpaces() {
        final String original = "\n \n ";
        final String expected = " \n \n";

        final String reversed = ReverseStringOrder.reverse(original);
        assertEquals(expected, reversed);
    }

    @Test
    public void testReversing() {
        final String original = "ЫЪabcde fghijklMnopqrstuvWxyz1\n23";
        final String expected = "32\n1zyxWvutsrqponMlkjihgf edcbaЪЫ";

        final String reversed = ReverseStringOrder.reverse(original);
        assertEquals(expected, reversed);
    }
}
