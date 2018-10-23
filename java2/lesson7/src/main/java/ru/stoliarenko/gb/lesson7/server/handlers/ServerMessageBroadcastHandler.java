package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.MessageTextBroadcast;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageBroadcastEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ServerMessageBroadcastHandler {
    @Inject
    private ConnectionsService connections;
    @Inject
    private MessageConverter converter;
    
    @SneakyThrows
    public void broadcast(@Observes final ServerMessageBroadcastEvent event) {
        ServerLogger.writeMessage("broadcasting...");
        final User user = connections.getUser(event.getConnection());
        if(user == null || user == User.NULL_USER) {
            //TODO response unauthorized
            ServerLogger.writeMessage("Unauthorized!");
            return;
        }
        ServerLogger.writeMessage("broadcast event" + event.getText());
        final MessageTextBroadcast message = converter.convertToMessage(event.getText(), MessageTextBroadcast.class);
        message.setText(user.getName() + ": " + message.getText());
        final String textMessage = converter.convertToString(message);
        for (Connection registredConnection : connections.getConnections()) {
            final User adressat = connections.getUser(registredConnection);
            if(adressat == null || adressat == User.NULL_USER) continue;
            registredConnection.send(textMessage);
        }
    }
}
