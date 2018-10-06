package ru.stoliarenko.gb.lesson7.client.api;

import java.net.Socket;

import ru.stoliarenko.gb.lesson7.MainInterface;

public interface Client extends MainInterface{
    Socket getSocket();
}
