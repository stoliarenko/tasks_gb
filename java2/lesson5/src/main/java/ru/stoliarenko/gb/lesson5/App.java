package ru.stoliarenko.gb.lesson5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Домашнее задание java-2 lesson-5
 * 
 * Задание:
 * Необходимо написать два метода, которые делают следующее:
 * 1) Создают одномерный длинный массив; 
 * 2) Заполняют этот массив единицами;
 * 3) Засекают время выполнения;
 * 4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
 *      arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 * 5) Проверяется время окончания метода System.currentTimeMillis();
 * 6) В консоль выводится время работы;
 * 
 * Отличие первого метода от второго:
 * Первый просто бежит по массиву и вычисляет значения.
 * Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
 */
@SuppressWarnings("unused")
public class App {
   public static void main( String[] args ){
      try {
         final Method rawArrayMethod = App.class.getDeclaredMethod("doRawArrayMath", float[].class);
         final Method doubleArrayTwoThreadsMethod = App.class.getDeclaredMethod("doDoubleArrayTwoThreadsMath", float[].class);
         final Method twoThreadsMethod = App.class.getDeclaredMethod("doTwoThreadsMath", float[].class);
         final Method goodPoolMethod = App.class.getDeclaredMethod("doGoodPoolMath", float[].class);
         final Method badPoolMethod = App.class.getDeclaredMethod("doBadPoolMath", float[].class);
         
         printCalculationsTime(rawArrayMethod);
         printCalculationsTime(doubleArrayTwoThreadsMethod);
         printCalculationsTime(twoThreadsMethod);
         printCalculationsTime(badPoolMethod);
         printCalculationsTime(goodPoolMethod);
      } catch (NoSuchMethodException e) {
         e.printStackTrace();
      }
   }
   /**
    * Методы, непосредственно выполняющие задачу:
    * 
    * 1. Обрабатывет весь массив в текущем потоке
    */
   private static void doRawArrayMath(final float[] bigArray) {
      if(bigArray == null) return;
      for (int i = 0; i < bigArray.length; i++) {
         bigArray[i] = doMath(bigArray[i], i);
      }
   }
   /**
    * 2. Обрабатывает массив в двух потоках с созданием вспомогательных массивов для каждого потока
    * @throws InterruptedException
    */
   private static void doDoubleArrayTwoThreadsMath(final float[] bigArray) throws InterruptedException{
      if(bigArray == null) return;
      final int arraysHalf = bigArray.length/2;
      final float[] firstHalfArray = new float[arraysHalf];
      System.arraycopy(bigArray, 0, firstHalfArray, 0,  firstHalfArray.length);
      final float[] secondHalfArray = new float[bigArray.length - arraysHalf];
      System.arraycopy(bigArray, arraysHalf, secondHalfArray, 0, secondHalfArray.length);
      
      final Thread firstThread = new Thread(() -> {
         for (int i = 0; i < firstHalfArray.length; i++) {
            firstHalfArray[i] = doMath(firstHalfArray[i], i);
         }
      }); 
      final Thread secondThread = new Thread(()->{
         for (int i = 0; i < secondHalfArray.length; i++) {
            secondHalfArray[i] = doMath(secondHalfArray[i], i+arraysHalf);
         }
      });
      firstThread.start();
      secondThread.start();
      firstThread.join();
      secondThread.join();
      
      System.arraycopy(firstHalfArray, 0, bigArray, 0, arraysHalf);
      System.arraycopy(secondHalfArray, 0, bigArray, arraysHalf, secondHalfArray.length);
   }
   /**
    * 3. Обрабатывает массив в двух потоках без создания вспомогательных массивов
    * @throws InterruptedException
    */
   private static void doTwoThreadsMath(final float[] bigArray) throws InterruptedException{
      if(bigArray == null) return;
      final int arraysHalf = bigArray.length/2;
      final Thread firstThread = new Thread(()->{
         for (int i = 0; i < arraysHalf; i++) {
            bigArray[i] = doMath(bigArray[i], i);
         }
      });
      final Thread secondThread = new Thread(()->{
         for (int i = arraysHalf; i < bigArray.length; i++) {
            bigArray[i] = doMath(bigArray[i], i);
         }
      });
      firstThread.start();
      secondThread.start();
      firstThread.join();
      secondThread.join();
   }
   /**
    * 4. Обрабатывает массив в пуле с указанием числа процессоров КАК БЫЛО СКАЗАНО НА ЛЕКЦИИ.
    * @throws InterruptedException
    */
   private static void doBadPoolMath(final float[] bigArray) throws InterruptedException{
      doPoolMath(bigArray, -1);
   }
   /**
    * 5. Обрабатывает массив в пуле с указанием числа процессоров как пишут в литературе.
    * @throws InterruptedException
    */
   private static void doGoodPoolMath(final float[] bigArray) throws InterruptedException{
      doPoolMath(bigArray, 1);
   }
   private static void doPoolMath(final float[] bigArray, int cpuShift) {
      if(bigArray == null) return;
      try {
         LoopHandler handler = new App().new LoopHandler(bigArray, Runtime.getRuntime().availableProcessors()+cpuShift);
         handler.loopProcess();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Определяет эффективность работы переданного метода
    * 
    * Создает массив на 10'000'000 элементов и присваивает им значение "1f"
    * после чего засекает время и выполняет переданный в параметре метод
    * Форматирует время работы и выводит в консоль
    * @param method
    */
   public static void printCalculationsTime(Method method) {
      if(method == null) return;
      final float[] bigArray = new float[10*1000*1000];
      Arrays.fill(bigArray, 1f);
      
      final long startTime = System.currentTimeMillis();
      try {
         method.invoke(null, bigArray);
      } catch (Exception e) {e.printStackTrace();}
      final long finishTime = System.currentTimeMillis();
      
      printResults(method.getName().substring(2), startTime, finishTime);
   }
   /**
    * Математическая операция в соответствии с заданием
    * @param oldValue - исходное значение
    * @param i - текущее значение итератора
    * @return - новое значение
    */
   private static float doMath(float oldValue, int i) {
      return (float)(oldValue * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
   }
   /**
    * Выводит в консоль переданные результаты работы метода
    * @param methodName - имя отработавшего метода
    * @param startTime - время начала работы метода
    * @param finishTime - время окончания работы метода
    */
   private static void printResults(String methodName, long startTime, long finishTime) {
      System.out.printf("%s calculations done in %.3fsec%n",methodName , (finishTime-startTime)/1000f);
   }
   
   /**
    * Класс, распределяющий цикличную нагрузку по указанному кол-ву потоков
    * взят из книги "Java™ Threads, Third Edition by Scott Oaks and Henry Wong"
    * изменен с учетом текущих задач
    * 
    * принимает массив значений для обработки и число потоков которое он должен создать
    * метод loopProcess() запускает выполнение задачи
    */
   private class LoopHandler implements Runnable {
      private float[] bigArray;
      private class LoopRange {
         public int start, end;
      }
      private Thread lookupThreads[];
      private int startLoop, endLoop, curLoop, numThreads;
      private LoopHandler(final float[] bigArray, int threads) {
         this.bigArray = bigArray;
         startLoop = curLoop = 0;
         endLoop = bigArray.length;
         numThreads = threads;
         lookupThreads = new Thread[numThreads];
      }
      private synchronized LoopRange loopGetRange() {
         if (curLoop >= endLoop) return null;
         final LoopRange result = new LoopRange();
         result.start = curLoop;
         curLoop += (endLoop-startLoop)/numThreads+1;
         result.end = (curLoop<endLoop) ? curLoop : endLoop;
         return result;
      }
      private void loopDoRange(int start, int end) {
         for (int i = start; i < end; i++) {
            bigArray[i] = doMath(bigArray[i], i);
         }
      }
      private void loopProcess() throws InterruptedException{
         for (int i = 0; i < numThreads; i++) {
            lookupThreads[i] = new Thread(this);
            lookupThreads[i].start();
         }
         for (int i = 0; i < numThreads; i++) {
            lookupThreads[i].join();
            lookupThreads[i] = null;
         }
      }
      public void run() {
         LoopRange str;
         while ((str = loopGetRange()) != null) {
             loopDoRange(str.start, str.end);
         }
      }
   }
}
