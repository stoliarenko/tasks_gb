package ru.stoliarenko.gb.lesson3;

import java.util.*;

/**
 * Домашнее задание java-2 урок-2 часть-1.
 * 
 * <p>
 * Задание:
 * Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). 
 * Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем). 
 * Посчитать, сколько раз встречается каждое слово.
 * </p>
 * 
 * @author Stoliarenko Alexander
 */
public class UniqueWordsCounter {
  /**
   * Создает массив строк указанного размера для 
   * последующего использования в методе {@link getUniqueWords}
   * 
   * @param numberOfWords - количество слов в создаваемом массиве
   * @return <code>new String[0]</code> если numberOfWords меньше одного
   *         и массив содержищий numberOfWords слов в остальных случаях
   */
  public static String[] createWordsArray(int numberOfWords) {
    if (numberOfWords < 1) return new String[0];
    final String[] WORDS = ("Collection Map List "
                          + "Set Queue ArrayList "
    	                  + "LinkedList SortedSet NavigableSet "
                          + "HashMap BlockingQueue").split(" ");
    List<String> resultList = new LinkedList<>();
    Random random = new Random();
    for (int i = 0; i < numberOfWords; i++) {
      int wordIndex = random.nextInt(WORDS.length);
      resultList.add(WORDS[wordIndex]);
	}
    return resultList.toArray(new String[0]);
  }
  /**
   * Определяет количество дубликатов во входном массиве строк
   * и возвращает новый массив строк, содержащий все уникальные слова
   * из входного массива с их количеством, порядок сохраняется. 
   *  
   * @param arrayOfWords - массив строк, который может содержать повторяющиеся значения
   * @return массив строк без повторяющихся значений, за каждым словом следует частота
   *         его появления в arrayOfWords отделенная " - "
   * @throws NullPointerException при неинициализированном входном массиве
   */
  public static String[] getUniqueWords(String[] arrayOfWords) {
    if (arrayOfWords == null) throw new NullPointerException("null array is not supported");
    Map<String, Integer> wordsMap = new LinkedHashMap<>();
    for (int i = 0; i < arrayOfWords.length; i++) {
      String word = arrayOfWords[i];
      Integer count = wordsMap.get(word);
      if (count == null) wordsMap.put(word, 1);
      else wordsMap.put(word, count + 1);
	}
    String[] result = new String[wordsMap.size()];
    int i = 0;
    for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
      result[i++] = entry.getKey() + " - " + entry.getValue();
    }
    return result;
  }
}
