package ru.stoliarenko.gb.lesson7.server.api;

import java.net.ServerSocket;

import ru.stoliarenko.gb.lesson7.MainInterface;

public interface Server extends MainInterface{
    ServerSocket getServerSocket();
    void run();
}
