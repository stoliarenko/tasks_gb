package ru.stoliarenko.gb.lesson6.client;

import ru.stoliarenko.gb.lesson6.User;

/**
 * Вспомогательный класс, заменяющий еще не введенный функционал
 * 
 * @author Stoliarenko Alexander
 */
public class ClientMessageHelper {
    public static void writeMessage(String text) {
        System.out.println(text);
    }
    /**
     * Имитирует ввод с консоли
     */
    public static String readMessage() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Some user text"; 
    }
    /**
     * Заменяет создание пользователя
     */
    public static User getUser() {
        return new User();
    }
}

