package ru.stoliarenko.gb.lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final Map<User, Connection> connections = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket sSocket = new ServerSocket(Configuration.port);
            ServerLogger.writeMessage("Server started.");
            while(true) {
                try {
                     ConnectionHandler handler = new ConnectionHandler(sSocket.accept());
                     handler.start();
                } catch (Exception e) {
                    sSocket.close();
                    ServerLogger.writeMessage("Server is down.");
                }
            }
        } catch (IOException e) {
            ServerLogger.writeMessage("Couldn't start a server.");
        }
    }
   
    private static class ConnectionHandler extends Thread{
        private Socket socket;
        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }
       
        @Override
        public void run() {
            try {
                Connection connection = new Connection(socket);
//                ServerLogger.writeMessage("Established connection with " + connection.getRemoteSocketAddress());
                try {
                    User user = handshake(connection);
                    try {
                        sendListOfUsers(user, connection);
                        recieveMessages(user, connection);
                    } catch (Exception lifeCycleException) {
                        try {
                            connection.close();
                        } catch (IOException cantCloseException) {ServerLogger.writeMessage("Can not close connection with " + user);}
                    } finally {
                        connections.remove(user);
                        broadcast(new Message(MessageType.USER_DISCONNECTED, user.toString()));
                        ServerLogger.writeMessage("User disconnected: " + user);
                    }
                } catch (ClassNotFoundException usernameException) {
                    ServerLogger.writeMessage("Can't create a user.");
                    try {
                        connection.close();
                    } catch (IOException cantCloseException) {ServerLogger.writeMessage("Can not close connection.");}
                }
            } catch(IOException connectionException) {ServerLogger.writeMessage("Failed to establish a connection.");}
            ServerLogger.writeMessage("Connection is closed.");
        }
        
        private User handshake(Connection connection) throws ClassNotFoundException, IOException{
            while (true) {
                Message nameRequest = new Message(MessageType.NAME_REQUEST);
                connection.send(nameRequest);
                Message userResponce = connection.receive();
                if(userResponce == null || userResponce.getType() != MessageType.USER_NAME ) continue;
                final User user = new User(userResponce.getText());
                if(connections.containsKey(user)) continue;
                connections.put(user, connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
                broadcast(new Message(MessageType.USER_CONNECTED, user.toString()));
                return user;
            }
        }
        private void sendListOfUsers(User user, Connection connection) throws IOException{
            for (User userInList : connections.keySet()) {
                if(user == userInList) continue;
                connection.send(new Message(MessageType.USER_CONNECTED, user.getName()));
            }
        }
        private void recieveMessages(User user, Connection connection) throws ClassNotFoundException, IOException{
            while(true) {
                Message recievedMessage = connection.receive();
                if(recievedMessage.getType() != MessageType.TEXT) {
                    ServerLogger.writeMessage("Incorrect type of message recieved from " + user);
                    continue;
                }
                broadcast(new Message(MessageType.TEXT, user + ": " + recievedMessage.getText()));
            }
        }
    }
   
    private static void broadcast(Message message) {
        for (Connection connection : connections.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                ServerLogger.writeMessage("Failed to broadcast a message.");
                e.printStackTrace();
            }
        }
    }
}
