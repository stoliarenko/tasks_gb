package ru.stoliarenko.gb.lesson7.server.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerLogger {
    public static void writeMessage(final String text) {
        if(text == null) return;
        final SimpleDateFormat formatter = new SimpleDateFormat("[HH:mm:ss] ");
        System.out.println(formatter.format(new Date()) + text);
    }
}
