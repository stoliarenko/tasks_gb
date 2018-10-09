package ru.stoliarenko.gb.lesson7.client.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientLogger {
    public static void writeMessage(String text) {
        final SimpleDateFormat formatter = new SimpleDateFormat("[HH:mm:ss] ");
        System.out.println(formatter.format(new Date()) + text);
    }
}
