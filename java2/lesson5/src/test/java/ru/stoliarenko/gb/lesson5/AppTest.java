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
      final Method GOOD_POOL_METHOD = App.class.getDeclaredMethod("doGoodPoolMath", float[].class);
      final Method BAD_POOL_METHOD = App.class.getDeclaredMethod("doBadPoolMath", float[].class);
      final Method NEUTRAL_POOL_METHOD = App.class.getDeclaredMethod("doNeutralPoolMath", float[].class);
      
      long startTime = System.currentTimeMillis();
      App.printCalculationsTime(GOOD_POOL_METHOD);
      long finishTime = System.currentTimeMillis();
      long goodMethodTime = finishTime - startTime;
      
      startTime = System.currentTimeMillis();
      App.printCalculationsTime(BAD_POOL_METHOD);
      finishTime = System.currentTimeMillis();
      long badMethodTime = finishTime - startTime;
      
      assertTrue(goodMethodTime < badMethodTime);
   }
}
