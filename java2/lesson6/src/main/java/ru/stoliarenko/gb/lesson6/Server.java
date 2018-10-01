package ru.stoliarenko.gb.lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class Server {
   private final Map<User, Connection> connections = new HashMap<>();

   public static void main(String[] args) {
      try {
         ServerSocket sSocket = new ServerSocket(Configuration.port);
         // TODO server is running message
         
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

}
