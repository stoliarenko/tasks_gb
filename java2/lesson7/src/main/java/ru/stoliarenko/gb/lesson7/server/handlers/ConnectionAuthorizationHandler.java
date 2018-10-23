package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.MessageUserConnected;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionAuthorizationEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.UsersService;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ConnectionAuthorizationHandler {
    @Inject
    private ConnectionsService connections;
    @Inject
    private MessageConverter converter;
    
    public void authorize(@ObservesAsync ConnectionAuthorizationEvent event) {
        final Connection connection = event.getConnection();
        final User user = event.getUser();
        if (connections.getUser(connection) != User.NULL_USER) return;
        
        connections.login(connection, user);
        final MessageUserConnected message = new MessageUserConnected();
        message.setUsername(user.getName());
        final String textMessage = converter.convertToString(message);
        
        for (Connection registredConnection : connections.getConnections()) {
            registredConnection.send(textMessage);
        }
        for (Connection registredConnection : connections.getConnections()) {
            final User registredUser = connections.getUser(registredConnection);
            if (registredUser == User.NULL_USER || registredUser == user) continue;
            message.setUsername(registredUser.getName());
            connection.send(converter.convertToString(message));
        }
    }
}
