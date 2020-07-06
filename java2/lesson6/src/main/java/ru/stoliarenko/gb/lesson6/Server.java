package ru.stoliarenko.gb.lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Серверная часть чата - обеспечивает 
 * добавление, хранение и удаление клиентов,
 * а так же отправку им сообщений
 * 
 * @author Stoliarenko Alexander
 */
public class Server {
    private static final Map<User, Connection> CONNECTIONS = new ConcurrentHashMap<>();

    private static Executor executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try {
            //Запуск сервера
            final ServerSocket sSocket = new ServerSocket(Configuration.PORT);
            ServerLogger.writeMessage("Server started.");
            //Пока сервер работает принимаем подключения и обрабатываем их
            //Сюда будут добавлены управляющие конструкции из GUI TODO
            while(true) {
                try {
                    executor.execute(new ConnectionHandler(sSocket.accept()));
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
        private final Socket socket;
        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }
       
        @Override
        public void run() {
            try {
                final Connection connection = new Connection(socket);
                try {
                    //Узнаём кто подключился и заносим в список активных
                    final User user = getUser(connection);
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
                        CONNECTIONS.remove(user);
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
        private User getUser(final Connection connection) throws ClassNotFoundException, IOException{
            if (connection == null) throw new IOException("null connection");
            while (true) {
                final Message nameRequest = new Message(MessageType.NAME_REQUEST);
                connection.send(nameRequest);
                final Message userResponce = connection.receive();
                if(userResponce == null || userResponce.getType() != MessageType.USER_NAME ) continue;
                final User user = new User(userResponce.getText());
                if(CONNECTIONS.containsKey(user)) continue;
                CONNECTIONS.put(user, connection);
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
        private void sendListOfUsers(final User user, final Connection connection) throws IOException{
            for (User userInList : CONNECTIONS.keySet()) {
                if(user == userInList) continue;
                connection.send(new Message(MessageType.USER_CONNECTED, userInList.getName()));
            }
        }
        /**
         * Принимает сообщения от пользователя
         * @param connection - соединение с пользователем
         */
        private void recieveMessages(final User user, final Connection connection) throws ClassNotFoundException, IOException{
            //Здесь будет управляюзая конструкция для иселючения из чата пользователя
            //по типу while(user.isNotKicked()) TODO
            while(true) {
                final Message recievedMessage = connection.receive();
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
    private static void broadcast(final Message message) {
        for (Connection connection : CONNECTIONS.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                ServerLogger.writeMessage("Failed to broadcast a message.");
                e.printStackTrace();
            }
        }
    }
}
