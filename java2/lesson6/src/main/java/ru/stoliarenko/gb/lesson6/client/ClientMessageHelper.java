package ru.stoliarenko.gb.lesson6.client;

import ru.stoliarenko.gb.lesson6.User;

public class ClientMessageHelper {
    public static void writeMessage(String text) {
        System.out.println(text);
    }
    public static String readMessage() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Some user text"; //TODO replace stub
    }
    public static User getUser() {
        return new User();
    }
}

