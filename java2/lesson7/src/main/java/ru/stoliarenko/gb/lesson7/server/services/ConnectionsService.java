package ru.stoliarenko.gb.lesson7.server.services;

import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.User;

@ApplicationScoped
public class ConnectionsService {
    private final Map<Connection, User> connections = new ConcurrentHashMap<>();
    
    public void addConnection(final Connection connection) {
        connections.put(connection, User.NULL_USER);
    }
    public void removeConnection(final Connection connection) {
        connections.remove(connection);
    }
    public void login(final Connection connection, final User user) {
        connections.put(connection, user);
    }
    public User logout(final Connection connection) {
        return connections.put(connection, User.NULL_USER);
    }
    public User getUser(final Connection connection) {
        return connections.get(connection);
    }
    public Set<Connection> getConnections(){
        return connections.keySet();
    }
    public Connection getConnection(final User user) {
        for (Connection connection : connections.keySet()) {
            if(connections.get(connection).equals(user)) return connection;
        }
        return null;
    }
}
