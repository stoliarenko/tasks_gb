package ru.stoliarenko.gb.lesson7.server.services;

import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import ru.stoliarenko.gb.lesson7.model.User;

@ApplicationScoped
public class ConnectionsService {
    private final Map<Socket, User> connections = new ConcurrentHashMap<>();
    
    public void addConnection(final Socket socket) {
        connections.put(socket, User.NULL_USER);
    }
    public void removeConnection(final Socket socket) {
        connections.remove(socket);
    }
    public void login(final Socket socket, final User user) {
        connections.put(socket, user);
    }
    public void logout(final Socket socket) {
        connections.put(socket, User.NULL_USER);
    }
    public User getUser(final Socket socket) {
        return connections.get(socket);
    }
    public Set<Socket> getConnections(){
        return connections.keySet();
    }
    public Socket getConnection(final User user) {
        for (Socket socket : connections.keySet()) {
            if(connections.get(socket).equals(user)) return socket;
        }
        return null;
    }
}
