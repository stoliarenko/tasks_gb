package ru.stoliarenko.gb.lesson3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
/**
 * Unit test for UniqueWordsCounter.
 */
public class UniqueWordsCounterTest {
/**
 * Tests for @method createWordsArray(int numberOfWords)
 */
  public static final String[] WORDS = ("Collection Map List "
                                      + "Set Queue ArrayList "
                                      + "LinkedList SortedSet NavigableSet "
                                      + "HashMap BlockingQueue").split(" ");
  public static final String[] TWO_TYPE_WORDS = new String[] {"One", "One", "One", 
                                                              "Two", "Two", "Two", 
                                                              "One", "Two", "One"};
  @Test
  public void zeroSizeTest() {
    String[] words = UniqueWordsCounter.createWordsArray(0);
    assertTrue(words.length == 0);
  }
  @Test
  public void realSizeTest() {
    String[] words = UniqueWordsCounter.createWordsArray(17);
    assertTrue(words.length == 17);
  }
  @Test
  public void negativeSizeTest() {
    String[] words = UniqueWordsCounter.createWordsArray(-12);
    assertTrue(words.length == 0);
  }
  @Test
  public void containsDuplicatesTest() {
    final int NUMBER_OF_UNIQUE_WORDS = WORDS.length;
    String[] words = UniqueWordsCounter.createWordsArray(5000);
    Set<String> noDuplicatesWords = new HashSet<>();
    Collections.addAll(noDuplicatesWords, words);
    assertTrue(noDuplicatesWords.size() == NUMBER_OF_UNIQUE_WORDS);
  }
  @Test
  public void containsAllWordsTest() {

    List<String> randomWords = Arrays.asList(UniqueWordsCounter.createWordsArray(100));
    assertTrue(randomWords.containsAll(Arrays.asList(WORDS)));
  }
  /**
   * Tests for @method getUniqueWords(String[] arrayOfWords)
   */
  @Test(expected = NullPointerException.class)
  public void nullInputArrayTest() {
    UniqueWordsCounter.getUniqueWords(null);
  }
  @Test
  public void noDublicatesTest() {
    String[] outputArray = UniqueWordsCounter.getUniqueWords(TWO_TYPE_WORDS);
    assertTrue(outputArray.length == 2);
  }
  @Test
  public void outputTest() {
    final String[] expectedResult = new String[] {"One - 5", "Two - 4"};
    String[] realResult = UniqueWordsCounter.getUniqueWords(TWO_TYPE_WORDS);
    assertArrayEquals(expectedResult, realResult);
  }
}
