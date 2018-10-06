package ru.stoliarenko.gb.lesson7.server.services;

public class ServerLogger {
    public static void writeMessage(final String text) {
        if(text == null) return;
        System.out.println(text);
    }
}
