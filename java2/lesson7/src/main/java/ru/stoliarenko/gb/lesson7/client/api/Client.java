package ru.stoliarenko.gb.lesson7.client.api;

import java.net.Socket;

import ru.stoliarenko.gb.lesson7.model.Connection;

public interface Client extends Runnable{
    Connection getConnection();
}
