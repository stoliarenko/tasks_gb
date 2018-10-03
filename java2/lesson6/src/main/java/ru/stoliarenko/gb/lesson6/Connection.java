package ru.stoliarenko.gb.lesson6;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Разделяет и синхронизирует входящий и исходящий потоки
 * а, так же оборачивает их в объектные потоки
 * 
 * @author Stoliarenko Alexander
 */
public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Message message) throws IOException{
        synchronized (out){
            out.writeObject(message);
        }
    }

    public Message receive() throws IOException, ClassNotFoundException{
        synchronized(in){
            return (Message)in.readObject();
        }
    }

    public void close() throws IOException{
        in.close();
        out.close();
        socket.close();
    }
}
