package ru.stoliarenko.gb.java3_lesson1;
/**
 * Домашняя работа java3-lesson1-2
 * 
 * Задание:
 * Написать метод, который преобразует массив в ArrayList;
 */
import java.util.ArrayList;

public final class ArrayToList {
    public static <T> ArrayList<T> toArrayList(final T[] array){
        if (array == null) return null;
        final ArrayList<T> resultList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            resultList.add(array[i]);
        }
        return resultList;
    }
}
