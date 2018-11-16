package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.server.api.Server;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionParseMessageEvent;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionReadMessageEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;

@ApplicationScoped
public final class ConnectionReadMessageHandler {
    
    @Inject
    private Server server;
    @Inject
    private ConnectionsService connections;
    
    @Inject
    private Event<ConnectionReadMessageEvent> readMessageEvent;
    @Inject
    private Event<ConnectionParseMessageEvent> parseMessageEvent;
    
    @SneakyThrows
    public void readMessage(@ObservesAsync final ConnectionReadMessageEvent event) {
        try {
            final String textMessage = event.getConnection().receive();
            server.getLogger().info(String.format("Received message: + %s from: %s", textMessage, event.getConnection().toString()));
            
            readMessageEvent.fireAsync(new ConnectionReadMessageEvent(event.getConnection()));
            parseMessageEvent.fire(new ConnectionParseMessageEvent(event.getConnection(), textMessage));
        } catch (Exception e) {
            final Connection connection = event.getConnection();
            connection.close();
            connections.removeConnection(connection);
            server.getLogger().info("Closed connection with " + connection.getRemoteSocketAddress());
            //TODO message user disconnected
        }
    }
    
}
