package ru.stoliarenko.gb.exceptions;

import java.util.logging.Logger;

public class LessonTwoHomework {
  public static void main( String[] args ){
    // Корректный массив
    String[][] testArray = new String[][] {{"1", "2", "3", "4"},
                                           {"5", "6", "7", "8"},
                                           {"9", "8", "7", "6"},
                                           {"5", "4", "3", "2"}};
    try {
     System.out.println("Sum of valid array is " + countSum(testArray, false) + "\n\n");
    } catch(MyArrayException me) {
      me.printStackTrace();
    }
    // Некорректный массив
    testArray[2][3] = "invalid";
    testArray[1][2] = "invalid";
    testArray[3][0] = "invalid";
    try {
      System.out.println("Sum of invalid array is " + countSum(testArray, true) + "\n\n");
    } catch(MyArrayException me) {
      me.printStackTrace();
    }
    // Массив?
    testArray = null;
    try {
      System.out.println("Sum of null array is " + countSum(testArray, true) + "\n\n");
    } catch(MyArrayException me) {
      me.printStackTrace();
    }
  }
  
  public static int countSum(String[][] stringArray, boolean ignoreInvalidData) throws MyArrayException{
    // Считаем отсутствие массива неправильным размером массива
    if (stringArray == null) throw new MyArraySizeException("404 array not found");
    if (stringArray.length != 4 || stringArray[0].length != 4) throw new MyArraySizeException();
    int result = 0;
    // В случае неправильных входных данных зарегистрируем все случаи
    Logger logger = Logger.getLogger("LoggingException");
    for (int row = 0; row < stringArray.length; row++) {
      for (int column = 0; column < stringArray[0].length; column++) {
        try {
          result += Integer.parseInt(stringArray[row][column]);
        } catch(NumberFormatException nfe) {
          if (ignoreInvalidData) logger.info(String.format("Invalid data at row[%d], column[%d]", row, column));
          else throw new MyArrayDataException(row, column);
        }
      }
    }
    return result;
  }
}
