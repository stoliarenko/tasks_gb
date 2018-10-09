package ru.stoliarenko.gb.lesson7.server.api;

import java.net.ServerSocket;

public interface Server extends Runnable{
    ServerSocket getServerSocket();
    void run();
}
