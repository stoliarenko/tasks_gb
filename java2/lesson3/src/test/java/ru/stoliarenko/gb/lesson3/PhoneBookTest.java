package ru.stoliarenko.gb.lesson3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
/**
 * Тесты класса PhoneBook.
 */
public class PhoneBookTest {
  /**
   * Тесты метода add(String name, String number).
   */
  @Test(expected = NullPointerException.class)
  public void addNullContactTest() {
    PhoneBook phoneBook = new PhoneBook();
    phoneBook.add(null, "12345678");
  }
  @Test(expected = NullPointerException.class)
  public void addNullNumberTest() {
    PhoneBook phoneBook = new PhoneBook();
    phoneBook.add("Veniamin", null);
  }
  @Test(expected = IllegalArgumentException.class)
  public void addInvalidContactTest() {
    PhoneBook phoneBook = new PhoneBook();
    phoneBook.add("", "12345678");
  }
  @Test(expected = IllegalArgumentException.class)
  public void addInvalidNumberTest() {
    PhoneBook phoneBook = new PhoneBook();
    phoneBook.add("Kolobok", "12345");
  }
  @Test
  public void addContactTest() {
    PhoneBook phoneBook = new PhoneBook();
    final String NAME = "Peace Duke";
    final String NUMBER_ONE = "+7(495)937-99-92";
    final String NUMBER_TWO = "+7(495)660-10-52";
    assertTrue(phoneBook.add(NAME, NUMBER_ONE));
    assertTrue(phoneBook.add(NAME, NUMBER_TWO));
    assertFalse(phoneBook.add(NAME, NUMBER_ONE));
  }
  /**
   * Тесты метода get(String name).
   */
  private static final PhoneBook TEST_PHONEBOOK = new PhoneBook();
  static {
    TEST_PHONEBOOK.add("Trus", "111-111");
    TEST_PHONEBOOK.add("Balbes", "222-222");
    TEST_PHONEBOOK.add("Bivaliy", "333-333");
    TEST_PHONEBOOK.add("Trus", "111-222");
    TEST_PHONEBOOK.add("Trus", "111-333");
  }
  @Test(expected = NullPointerException.class)
  public void getNullContactTest() {
    TEST_PHONEBOOK.get(null);
  }
  @Test(expected = IllegalArgumentException.class)
  public void getNonExistingContactTest() {
    TEST_PHONEBOOK.get("NeTrus");
  }
  @Test
  public void singleValueTest() {
    Set<String> numbers = TEST_PHONEBOOK.get("Balbes");
    assertTrue(numbers.size() == 1);
    assertTrue(numbers.iterator().next().equals("222-222"));
  }
  @Test
  public void multiValueTest() {
    Set<String> numbers = TEST_PHONEBOOK.get("Trus");
    assertTrue(numbers.size() == 3);
    assertTrue(numbers.contains("111-111"));
    assertTrue(numbers.contains("111-222"));
    assertTrue(numbers.contains("111-333"));
    assertFalse(numbers.contains("222-222"));
  }
}
