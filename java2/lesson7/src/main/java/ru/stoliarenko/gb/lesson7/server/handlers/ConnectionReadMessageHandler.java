package ru.stoliarenko.gb.lesson7.server.handlers;

import java.io.DataInputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionParseMessageEvent;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionReadMessageEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerAwaitUserAuthorizationEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;

@ApplicationScoped
public final class ConnectionReadMessageHandler {
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
            ServerLogger.writeMessage("Received message:" + textMessage);
            
            parseMessageEvent.fireAsync(new ConnectionParseMessageEvent(event.getConnection(), textMessage));
            readMessageEvent.fireAsync(new ConnectionReadMessageEvent(event.getConnection()));
        } catch (Exception e) {
            final Connection connection = event.getConnection();
            connection.close();
            connections.removeConnection(connection);
            ServerLogger.writeMessage("Closed connection with " + connection.getRemoteSocketAddress());
            //TODO message user disconnected
        }
    }
}
