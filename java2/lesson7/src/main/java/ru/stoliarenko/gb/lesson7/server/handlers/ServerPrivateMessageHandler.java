package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.MessageTextPrivate;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessagePrivateEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;
import ru.stoliarenko.gb.lesson7.server.services.UsersService;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ServerPrivateMessageHandler {
    @Inject
    private ConnectionsService connections;
    @Inject
    private UsersService users;
    @Inject
    private MessageConverter converter;
    
    @SneakyThrows
    public void sendPrivateMessage(@Observes final ServerMessagePrivateEvent event) {
        ServerLogger.writeMessage("private msg handler..." + event.getText());
        final User sender = connections.getUser(event.getConnection());
        if(sender == null || sender == User.NULL_USER) {
            //TODO not authorized event
            return;
        }
        final MessageTextPrivate message = converter.convertToMessage(event.getText(), MessageTextPrivate.class);
        final User receiver = users.getByName(message.getUsername());
        ServerLogger.writeMessage("Receiver is " + receiver);
        if (receiver == null || receiver == User.NULL_USER) {
            //TODO no such user event
            return;
        }
        final Connection receiverConnection = connections.getConnection(receiver);
        ServerLogger.writeMessage("Receivers socket is " + receiverConnection);
        if(receiverConnection == null) {
            //TODO user offline event
            return;
        }
        message.setText(String.format("%s whispers to you: %s", sender.getName(), message.getText()));
        receiverConnection.send(converter.convertToString(message));
        //TODO responce whisper success
    }
}
