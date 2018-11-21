package ru.stoliarenko.gb.lesson7.model;

public final class Assert {
    public static void assertTrue(boolean statement) throws AssertionException{
        if (!statement) throw new AssertionException();
    }
}
