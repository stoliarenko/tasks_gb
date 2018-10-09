package ru.stoliarenko.gb.lesson7.client.api;

import java.net.Socket;

import ru.stoliarenko.gb.lesson7.MainInterface;
import ru.stoliarenko.gb.lesson7.model.Connection;

public interface Client extends MainInterface{
    Connection getConnection();
}
