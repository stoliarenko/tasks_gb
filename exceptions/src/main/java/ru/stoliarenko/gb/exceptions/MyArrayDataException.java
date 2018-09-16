package ru.stoliarenko.gb.exceptions;

public class MyArrayDataException extends MyArrayException{
  public MyArrayDataException() {
    super();
  }
  public MyArrayDataException(String message) {
    super(message);
  }
  public MyArrayDataException(int row, int column) {
    super(String.format("Invalid data at row[%d] column[%d]", row, column));
  }
}
