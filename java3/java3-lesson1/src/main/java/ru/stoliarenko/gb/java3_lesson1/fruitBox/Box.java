package ru.stoliarenko.gb.java3_lesson1.fruitBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Домашняя работа java3-lesson1-3
 * 
 * Задание:
 * Есть классы Fruit -> Apple, Orange (больше фруктов не надо)
 * Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, 
 * поэтому в одну коробку нельзя сложить и яблоки, и апельсины.
 * Для хранения фруктов внутри коробки можно использовать ArrayList.
 * Сделать метод getWeight(), который высчитывает вес коробки, зная количество фруктов и вес одного фрукта 
 * (вес яблока – 1.0f, апельсина – 1.5f. Не важно, в каких это единицах).
 * Внутри класса Коробка сделать метод compare, который позволяет сравнить текущую коробку с той, 
 * которую подадут в compare в качестве параметра, true – если она равны по весу, false – в противном случае 
 * (коробки с яблоками мы можем сравнивать с коробками с апельсинами).
 * Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую 
 * (помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами). 
 * Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке.
 * 
 * @author Stoliarenko Alexander
 *
 * @param <E> - тип фрукта в коробке
 */
public final class Box<E extends Fruit> {
    private List<E> content = new ArrayList<E>();
    
    public boolean add(final E element) {
        return content.add(element);
    }
    
    public float getWeight() {
        float totalWeight = 0;
        for (E element : content) {
            totalWeight += element.getWeight();
        }
        return totalWeight;
    }
    
    public boolean compare(final Box<? extends Fruit> secondBox) {
        return getWeight() == secondBox.getWeight();
    }
    
    public void pour(final Box<E> secondBox) {
        while(content.size() > 0) {
            secondBox.add(content.get(content.size()-1));
            content.remove(content.size()-1);        }
    }
}
