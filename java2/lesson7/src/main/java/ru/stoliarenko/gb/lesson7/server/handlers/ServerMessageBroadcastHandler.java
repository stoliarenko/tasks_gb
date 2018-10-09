package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
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
    public void broadcast(@ObservesAsync final ServerMessageBroadcastEvent event) {
        final User user = connections.getUser(event.getConnection());
        if(user == null || user == User.NULL_USER) {
            //TODO responce unauthorized
            return;
        }
        ServerLogger.writeMessage("broadcast event" + event.getText());
        final MessageTextBroadcast message = converter.convertToMessage(event.getText(), MessageTextBroadcast.class);
        message.setText(user.getName() + ": " + message.getText());
        final String textMessage = converter.convertToString(message);
        for (Connection registredConnection : connections.getConnections()) {
            registredConnection.send(textMessage);
        }
    }
}
