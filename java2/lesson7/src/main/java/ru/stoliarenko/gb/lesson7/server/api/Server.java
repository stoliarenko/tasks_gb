package ru.stoliarenko.gb.lesson7.server.api;

import java.net.ServerSocket;
import java.util.logging.Logger;

public interface Server extends Runnable{
    Logger getLogger();
    ServerSocket getServerSocket();
    void run();
}
