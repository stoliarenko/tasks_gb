package ru.stoliarenko.gb.lesson7.model;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import lombok.SneakyThrows;

public final class Connection implements Closeable{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    
    @SneakyThrows
    public Connection(Socket socket) {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }
    
    @SneakyThrows
    public void send(String message) {
        synchronized (out) {
            out.writeUTF(message);
        }
    }
    
    @SneakyThrows
    public String receive() {
        synchronized (in) {
            return in.readUTF();
        }
    }
    
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
    
    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    public String toString() {
        return socket.getLocalSocketAddress().toString();
    }
}
