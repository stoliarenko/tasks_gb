package ru.stoliarenko.gb.exceptions;

public class MyArraySizeException extends MyArrayException{
  public MyArraySizeException() {
	super("Недопустимый размер массива");
  }
  public MyArraySizeException(String message) {
	super(message);
  }
}
