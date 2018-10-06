package ru.stoliarenko.gb.lesson7.server.handlers;

import java.io.DataOutputStream;
import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.MessageTextPrivate;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ServerNoSuchUserEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerSendPrivateEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerUserOfflineEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;
import ru.stoliarenko.gb.lesson7.server.services.UsersService;

@ApplicationScoped
public final class ServerPrivateMessageHandler {
    @Inject
    private ConnectionsService connections;
    @Inject
    private UsersService users;
    @Inject
    private Event<ServerUserOfflineEvent> userOffline;
    @Inject
    private Event<ServerNoSuchUserEvent> noSuchUser;
    
    @SneakyThrows
    public void sendPrivateMessage(@ObservesAsync final ServerSendPrivateEvent event) {
        ServerLogger.writeMessage("private msg handler..." + event.getText());
        final User sender = connections.getUser(event.getSocket());
        if(sender == null) return;
        final ObjectMapper mapper = new ObjectMapper();
        final MessageTextPrivate message = mapper.readValue(event.getText(), MessageTextPrivate.class);
        final User receiver = users.getByName(message.getUsername());
        ServerLogger.writeMessage("Receiver is " + receiver);
        if (receiver == null) {
            //TODO no such user event
            return;
        }
        final Socket receiverSocket = connections.getConnection(receiver);
        ServerLogger.writeMessage("Receivers socket is " + receiverSocket);
        if(receiverSocket == null) {
            //TODO user offline event
            return;
        }
        message.setText(String.format("%s whispers to you: %s", sender.getName(), message.getText()));
        final DataOutputStream out = new DataOutputStream(receiverSocket.getOutputStream());
        out.writeUTF(mapper.writeValueAsString(message));
    }
}
