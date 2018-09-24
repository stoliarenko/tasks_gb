package ru.stoliarenko.gb.lesson3;

import java.util.*;

/**
 * Домашнее задание java-2 урок-3 часть-2.
 * 
 * <p>
 * Задание:
 * Написать простой класс Телефонный Справочник, 
 * который хранит в себе список фамилий и телефонных номеров. 
 * В этот телефонный справочник с помощью метода add() можно добавлять записи, 
 * а с помощью метода get() искать номер телефона по фамилии. Следует учесть, 
 * что под одной фамилией может быть несколько телефонов (в случае однофамильцев), 
 * тогда при запросе такой фамилии должны выводиться все телефоны.
 * </p>
 * 
 * @author Stoliarenko Alexander
 */
public class PhoneBook {
  private static Map<String, Set<String>> phoneBook = new TreeMap<>();
  /**
   * Добавляет запись в книгу, 
   * если запись существует, а немер нет - добавляет к записи новый номер.
   * 
   * @param name - имя контакта
   * @param number - номер телефона контакта
   * @return <code>true</code> - если контакт был добавлен
   * @return <code>false</code> - если контакт уже был в книге
   * @throws NullPointerException - если любой из параметров имеет значение null
   * @throws IllegalArgumentException - если любой из параметров не может быть добавлен в книгу
   */
  public boolean add(String name, String number) {
    // Validating input
    if (name == null || number == null) 
       throw new NullPointerException("null contacts are not supported");
    if (name.length() > 32 || name.length() == 0) 
       throw new IllegalArgumentException("Illegal contact name");
    if (number.length() > 16 || number.length() < 6)
       throw new IllegalArgumentException("Illegal contact number");
    
    // Proceeding
    Set<String> numbers = phoneBook.get(name);
    if (numbers == null) {
      numbers = new HashSet<String>();
      numbers.add(number);
      phoneBook.put(name, numbers);
      return true;
    }
    return numbers.add(number);
  }
  /**
   * Возвращает множество номеров контакта
   * 
   * @param name - имя контакта
   * @return <code>HashSet<String></code> содержащий номера контакта name
   * @throws NullPointerException - если name имеет значение null
   * @throws IllegalArgumentException - если такого контакта не существует
   */
  public Set<String> get(String name) {
    if (name == null) throw new NullPointerException("Имя не может принимать значение null");
    Set<String> numbers = phoneBook.get(name);
    if (numbers == null) throw new IllegalArgumentException("Такого контакта не существует");
    return numbers;
  }
}
