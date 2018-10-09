package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.MessageUserDisconnected;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageBroadcastEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageLogoutEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ServerMessageLogoutHandler {
    @Inject
    private Event<ServerMessageBroadcastEvent> broadcast;
    @Inject
    private ConnectionsService connections;
    @Inject
    private MessageConverter converter;

    public void logout(@ObservesAsync final ServerMessageLogoutEvent event) {
        final User user = connections.logout(event.getConnection());
        if (user==null || user == User.NULL_USER) return;
        
        ServerLogger.writeMessage(String.format("User %s disconnected.", user.getName()));
        final MessageUserDisconnected message = new MessageUserDisconnected();
        message.setUsername(user.getName());
        final String textMessage = converter.convertToString(message);

        for (Connection registredConnection : connections.getConnections()) {
            registredConnection.send(textMessage);
        }
    }
}
