package ru.stoliarenko.gb.lesson6.client;

import java.io.IOException;
import java.net.Socket;

import ru.stoliarenko.gb.lesson6.PonyChatClient;
import ru.stoliarenko.gb.lesson6.Configuration;
import ru.stoliarenko.gb.lesson6.Connection;
import ru.stoliarenko.gb.lesson6.Message;
import ru.stoliarenko.gb.lesson6.MessageType;
import ru.stoliarenko.gb.lesson6.User;
/**
 * Клиентская часть чата - базовый вариант - 
 * обеспечивает передачу, прием и обработку сообщений
 * 
 * @author Stoliarenko Alexander
 */
public class Client extends Thread{
    protected User user;
    protected Connection connection;
    protected final PonyChatClient view;
    protected volatile boolean isConnected = false;

    public Client(PonyChatClient view) {
        this.view = view;
    }
    //Отдельный поток нужен в случае работы с консолью, где в нем ожидается ввод.
    //Для GUI не используется
    @Override
    public void run() {
        final messageReader reader = new messageReader();
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
        //Блок для работы с консолью, если понадобится - снять комментарий.
//        while(isConnected) {
//            final String userInput = ClientMessageHelper.readMessage();
//            if ("quit".equals(userInput)) isConnected = false;
//            else sendMessage(userInput);
//        }
    }
    /**
     * Вспомогательный класс выполняет всю работу, 
     * пока внешний класс делает вид что принимает сообщения из консоли
     * 
     * @author Stoliarenko Alexander
     */
    private class messageReader extends Thread{
        @Override
        public void run() {
            try {
                final String serverAddress = Configuration.ADDRESS; //TODO change this trash to properties file
                final int serverPort = Configuration.PORT;
                final Socket socket = new Socket(serverAddress, serverPort);
                connection = new Connection(socket);
                registerUser(connection);
                receiveMessages(connection);
            } catch (Exception e) {
                notifyConnectionStatusChanged(false);//нужно для прекращения работы потоков при работе с консолью
            }
        }
        /**
         * Создает пользователя и регистрирует его на сервере
         * 
         * на данном этапе GUI не готов принимать имя пользователя и он генерируется автоматически
         * @param connection - соединение с сервером
         */
        protected void registerUser(Connection connection) throws Exception{
            if (connection == null) return;
            User createdUser = null;
            while(true) {
                Message serverMessage = connection.receive();
                if(serverMessage.getType() == MessageType.NAME_REQUEST) {
                    createdUser = ClientMessageHelper.getUser();
                    final Message usernameMessage = new Message(MessageType.USER_NAME, createdUser.toString());
                    connection.send(usernameMessage);
                }else if(serverMessage.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    ClientMessageHelper.writeMessage("Connected!");
                    user = createdUser;
                    view.setTitle("Pony Chat - user: " + createdUser.toString());
                    return;
                }else throw new IOException("Unexpected message");
            }
        }
        /**
         * Принимает сообщения от пользователя и обрабатывает в соответствии с типом
         * 
         * @param connection - соединение с сервером
         */
        // Почему-то не работает через свич?!!!
        protected void receiveMessages(Connection connection) throws Exception{
            if (connection == null) return;
            //Здесь будет управляющая конструкция для отключения по нажатию кнопки TODO
            while(true) {
                final Message serverMessage = connection.receive();
                if (serverMessage.getType() == MessageType.TEXT) {
                    showIncomingMessage(serverMessage.getText());
                } else if (serverMessage.getType() == MessageType.USER_CONNECTED) {
                    addUser(serverMessage.getText());
                } else if (serverMessage.getType() == MessageType.USER_DISCONNECTED) {
                    deleteUser(serverMessage.getText());
                } else
                    throw new IOException("Unexpected message");
            }
        }
        /**
         * Отображает полученное текстовое сообщение
         * @param text - текст сообщения
         */
        protected void showIncomingMessage(String text) {
            ClientMessageHelper.writeMessage(text);
            view.showMessage(text);
        }
        /**
         * Обрабатывает добавление пользователя
         * @param username - имя пользователя
         */
        protected void addUser(String username) {
            if(username == null) return;
            if(username.equals(user.getName())) username = "(Я)"+ username;
            ClientMessageHelper.writeMessage("User connected: " + username);
            view.addUser(username);
        }
        /**
         * Обрабатывает удаление пользователя
         * @param username - имя пользователя
         */
        protected void deleteUser(String username) {
            if(username == null) return;
            ClientMessageHelper.writeMessage("User disconnected: " + username.toString());
            view.deleteUser(username);
        }
        /**
         * Обеспечивает завершение работы потоков при работе с консолью
         * @param isConnected - статус соединения
         */
        protected void notifyConnectionStatusChanged(boolean isConnected) {
            Client.this.isConnected = isConnected;
            synchronized(Client.this) {
                Client.this.notify();
            }
        }
    }
    /**
     * Отправляет текстовое сообзение на сервер
     * @param text - текст сообщения
     */
    public void sendMessage(String text) {
        if(text == null) return;
        Message message = new Message(MessageType.TEXT, text);
        try {
            connection.send(message);
        } catch (IOException e) {
            isConnected = false;
            ClientMessageHelper.writeMessage("Connection is down.");
        }
    }
}
