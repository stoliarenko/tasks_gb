package ru.stoliarenko.gb.lesson7.model;

public final class AssertionException extends RuntimeException{
    public AssertionException() {
        super("Assertion failed");
    }
}
