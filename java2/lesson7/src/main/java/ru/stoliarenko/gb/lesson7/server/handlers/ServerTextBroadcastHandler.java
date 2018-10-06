package ru.stoliarenko.gb.lesson7.server.handlers;

import java.io.DataOutputStream;
import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.MessageTextBroadcast;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ServerTextBroadcastEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;

@ApplicationScoped
public final class ServerTextBroadcastHandler {
    @Inject
    private ConnectionsService connections;
    
    @SneakyThrows
    public void broadcast(@ObservesAsync final ServerTextBroadcastEvent event) {
        ServerLogger.writeMessage("broadcast event" + event.getText());
        final User user = connections.getUser(event.getSocket());
        if(user == null) return;
        final ObjectMapper mapper = new ObjectMapper();
        final MessageTextBroadcast message = mapper.readValue(event.getText(), MessageTextBroadcast.class);
        message.setText(user.getName() + ": " + message.getText());
        final String messageAsText = mapper.writeValueAsString(message);
        for (Socket socket : connections.getConnections()) {
            final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(messageAsText);
        }
    }
}
