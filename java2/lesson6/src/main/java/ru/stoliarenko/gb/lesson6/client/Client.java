package ru.stoliarenko.gb.lesson6.client;

import java.io.IOException;
import java.net.Socket;

import ru.stoliarenko.gb.lesson6.Configuration;
import ru.stoliarenko.gb.lesson6.Connection;
import ru.stoliarenko.gb.lesson6.Message;
import ru.stoliarenko.gb.lesson6.MessageType;
import ru.stoliarenko.gb.lesson6.User;

public class Client extends Thread{
    protected Connection connection;
    protected volatile boolean isConnected = false;
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
    @Override
    public void run() {
        messageReader reader = new messageReader();
        reader.setDaemon(true);
        reader.start();
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException ie) {
            ClientMessageHelper.writeMessage("Interrupted connection.");
            isConnected = false;
            return;
        }
        ClientMessageHelper.writeMessage(isConnected ? "Connection established." : "No connection.");
        while(isConnected) {
            String userInput = ClientMessageHelper.readMessage();
            if ("quit".equals(userInput)) isConnected = false;
            else sendMessage(userInput);
        }
    }
    private class messageReader extends Thread{
        @Override
        public void run() {
            try {
                String serverAddress = Configuration.address; //TODO change this trash to properties file
                int serverPort = Configuration.port;
                Socket socket = new Socket(serverAddress, serverPort);
                connection = new Connection(socket);
                handshake(connection);
                recieveMessages(connection);
            } catch (Exception e) {
                notifyConnectionStatusChanged(false);
            }
        }
        protected void handshake(Connection connection) throws Exception{
            while(true) {
                Message serverMessage = connection.receive();
                if(serverMessage.getType() == MessageType.NAME_REQUEST) {
                    Message usernameMessage = new Message(MessageType.USER_NAME, ClientMessageHelper.getUser().toString());
                    connection.send(usernameMessage);
                }else if(serverMessage.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    ClientMessageHelper.writeMessage("Connected!");
                    return;
                }else throw new IOException("Unexpected message");
            }
        }
        // Почему не работает через свич?!!!
        protected void recieveMessages(Connection connection) throws Exception{
            while(true) {
                Message serverMessage = connection.receive();
                if(serverMessage.getType() == MessageType.TEXT) {
                    ClientMessageHelper.writeMessage(serverMessage.getText());
                }else if(serverMessage.getType() == MessageType.USER_CONNECTED) {
                    ClientMessageHelper.writeMessage("User connected: " + serverMessage.getText());
                }else if(serverMessage.getType() == MessageType.USER_DISCONNECTED) {
                    ClientMessageHelper.writeMessage("User disconnected: " + serverMessage.getText());
                }else throw new IOException("Unexpected message");
            }
        }
        protected void showIncomingMessage(String text) {
            ClientMessageHelper.writeMessage(text);
        }
        protected void informUserAdded(String username) {
            ClientMessageHelper.writeMessage("User connected: " + username.toString());
        }
        protected void informUserDeleted(String username) {
            ClientMessageHelper.writeMessage("User disconnected: " + username.toString());
        }
        protected void notifyConnectionStatusChanged(boolean isConnected) {
            Client.this.isConnected = isConnected;
            synchronized(Client.this) {
                Client.this.notifyAll();
            }
        }
    }
    
    protected User getUser() {
        return new User();
    }
    protected messageReader getMessageReader() {
        return new messageReader();
    }
    protected void sendMessage(String text) {
        Message message = new Message(MessageType.TEXT, text);
        try {
            connection.send(message);
        } catch (IOException e) {
            isConnected = false;
            ClientMessageHelper.writeMessage("Connection is down.");
        }
    }
}
