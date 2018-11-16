package ru.stoliarenko.gb.lesson6;

/**
 * Homework java-3 lesson-6
 * 
 * @author Stoliarenko Alexander
 */
public class App {
    
    /**
     * Задание 1:
     * Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив. 
     * Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива 
     * элементов, идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, 
     * иначе в методе необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных). 
     * Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
     */
    public static int[] splitByLast4(final int[] inputArray) {
        int last4Position = -1;
        for (int i = inputArray.length - 1; i >= 0; i--) {
            if (inputArray[i] != 4) continue;
            last4Position = i;
            break;
        }
        if (last4Position == -1) throw new RuntimeException("Array must contain atleast one '4'!");
        if (last4Position == inputArray.length - 1) return new int[0];
        
        final int[] resultArray = new int[inputArray.length - (last4Position + 1)];
        System.arraycopy(inputArray, last4Position + 1, resultArray, 0, resultArray.length);
        return resultArray;
    }
    
     /**
     * Задание 2:
     * Написать метод, который проверяет состав массива из чисел 1 и 4. 
     * Если в нем нет хоть одной четверки или единицы, то метод вернет false; 
     * Написать набор тестов для этого метода (по 3-4 варианта входных данных).
     */
    public static boolean checkArrayOf1And4(final int[] inputArray) {
        if (inputArray.length == 0) return false;
        for (int i = 0; i < inputArray.length; i++) {
            final int value = inputArray[i];
            if (value != 1 && value != 4) return false;
        }
        return true;
    }
    
}
