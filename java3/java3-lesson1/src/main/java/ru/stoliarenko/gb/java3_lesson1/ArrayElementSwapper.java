package ru.stoliarenko.gb.java3_lesson1;

/**
 * Домашняя работа java3-lesson1-1
 * 
 * Задание:
 * Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
 * Реализована замена первого и последнего элементов.
 * 
 * @param array - массив, в котором элементы будут меняться местами
 */
public final class ArrayElementSwapper {
    public static <T> void swap(final T[] array) {
        if(array == null || array.length < 2) return;
        final int firstElement = 0;
        final int lastElement = array.length - 1;
        final T holder = array[lastElement];
        
        array[lastElement] = array[firstElement];
        array[firstElement] = holder;
    }
}
