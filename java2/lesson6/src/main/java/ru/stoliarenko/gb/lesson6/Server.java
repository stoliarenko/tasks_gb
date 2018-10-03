package ru.stoliarenko.gb.lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Серверная часть чата - обеспечивает 
 * добавление, хранение и удаление клиентов,
 * а так же отправку им сообщений
 * 
 * @author Stoliarenko Alexander
 */
public class Server {
    private static final Map<User, Connection> connections = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            //Запуск сервера
            ServerSocket sSocket = new ServerSocket(Configuration.port);
            ServerLogger.writeMessage("Server started.");
            //Пока сервер работает принимаем подключения и обрабатываем их
            //Сюда будут добавлены управляющие конструкции из GUI TODO
            while(true) {
                try {
                    ConnectionHandler handler = new ConnectionHandler(sSocket.accept());
                    handler.start();
                } catch (Exception e) {
                    sSocket.close();
                    ServerLogger.writeMessage("Server is down.");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            ServerLogger.writeMessage("Couldn't start a server.");
        }
    }
    /**
     * Вспомогательный класс, предназначенный для работы
     * с каждым пользователем в отдельном потоке
     * 
     * @author Stoliarenko Alexander
     */
    private static class ConnectionHandler extends Thread{
        private Socket socket;
        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }
       
        @Override
        public void run() {
            try {
                Connection connection = new Connection(socket);
                try {
                    //Узнаём кто подключился и заносим в список активных
                    User user = getUser(connection);
                    try {
                        //Отправляем свежеподключившемуся список тех, кто уже в сети
                        //и принимаем сообщения
                        sendListOfUsers(user, connection);
                        recieveMessages(user, connection);
                    } catch (Exception lifeCycleException) {
                        try {
                            connection.close();
                        } catch (IOException cantCloseException) {ServerLogger.writeMessage("Can not close connection with " + user);}
                    } finally {
                        //По окончании приема сообщений удаляем пользователя из списков и сообщаем всем об этом
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
        }
        /**
         * Метод запрашивает имя пользователя и отправляет подтверждение
         * если имя получено и доступно для использования иначе повторяет запрос.
         * Прошедшее проверку имя регистрируется и рассылается всем активным участникам
         * @param connection - соединение с новым пользователем
         * @return - созданный экземпляр нового пользователя.
         */
        private User getUser(Connection connection) throws ClassNotFoundException, IOException{
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
        /**
         * Отправляет подключившемуся пользователю список участников чата
         * @param user - подключившийся пользователь
         * @param connection - соединение с подключившимся пользователем
         */
        private void sendListOfUsers(User user, Connection connection) throws IOException{
            for (User userInList : connections.keySet()) {
                if(user == userInList) continue;
                connection.send(new Message(MessageType.USER_CONNECTED, userInList.getName()));
            }
        }
        /**
         * Принимает сообщения от пользователя
         * @param connection - соединение с пользователем
         */
        private void recieveMessages(User user, Connection connection) throws ClassNotFoundException, IOException{
            //Здесь будет управляюзая конструкция для иселючения из чата пользователя
            //по типу while(user.isNotKicked()) TODO
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
    /**
     * Отправляет переданное сообщение всем активным участникам чата 
     * @param message
     */
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
