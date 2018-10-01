package ru.stoliarenko.gb.lesson5;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
/**
 * Убеждаемся что создавать меньше потоков чем 
 * имеем ядеp/роцессоров - как минимум не эффективно.
 */
public class AppTest {
   @Test
   public void sanityTest() throws NoSuchMethodException{
      final Method goodPoolMethod = App.class.getDeclaredMethod("doGoodPoolMath", float[].class);
      final Method badPoolMethod = App.class.getDeclaredMethod("doBadPoolMath", float[].class);
      
      long startTime = System.currentTimeMillis();
      App.printCalculationsTime(goodPoolMethod);
      long finishTime = System.currentTimeMillis();
      long goodMethodTime = finishTime - startTime;
      
      startTime = System.currentTimeMillis();
      App.printCalculationsTime(badPoolMethod);
      finishTime = System.currentTimeMillis();
      long badMethodTime = finishTime - startTime;
      
      assertTrue(goodMethodTime < badMethodTime);
   }
}
