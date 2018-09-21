package ru.stoliarenko.gb.exceptions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for java2/lesson2 homework.
 */
public class AppTest {
    @Test(expected = ru.stoliarenko.gb.exceptions.MyArraySizeException.class)
    public void nullMassiveTest() throws MyArrayException{
      LessonTwoHomework.countSum(null, true);
    }
    @Test(expected = ru.stoliarenko.gb.exceptions.MyArraySizeException.class)
    public void wrongSizeTest() throws MyArrayException{
      LessonTwoHomework.countSum(new String[1][3], true);
    }
    @Test(expected = ru.stoliarenko.gb.exceptions.MyArrayDataException.class)
    public void corruptedDataNotIgnoredTest() throws MyArrayException{
      String[][] array = new String[][]{{"1","a","1","b"},
                                        {"c","1","d","1"},
                                        {"1","f","1","e"},
                                        {"g","1","h","1"}};
      LessonTwoHomework.countSum(array, false);
    }
    @Test
    public void corruptedDataIgnoredTest() throws MyArrayException{
      String[][] array = new String[][]{{"1","a","1","b"},
                                        {"c","1","d","1"},
                                        {"1","f","1","e"},
                                        {"g","1","h","1"}};
      int sum = LessonTwoHomework.countSum(array, true);
      assertTrue(sum == 8);
    }
    @Test
    public void validDtaTest() throws MyArrayException{
      String[][] array = new String[][]{{"1","2","3","4"},
                                        {"5","6","7","8"},
                                        {"9","8","7","6"},
                                        {"5","4","3","2"}};
      int sum = LessonTwoHomework.countSum(array, false);
      assertTrue(sum == 80);
    }
}
