package ru.stoliarenko.gb.lesson7.server.handlers;

import java.io.DataOutputStream;
import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.MessageUserConnected;
import ru.stoliarenko.gb.lesson7.server.events.ServerLoginAcceptedEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;

@ApplicationScoped
public final class ServerLoginAcceptedHandler {
    @Inject
    private ConnectionsService connections;
    
    @SneakyThrows
    public void informAccepted(@ObservesAsync ServerLoginAcceptedEvent event) {
        connections.login(event.getSocket(), event.getUser());
        final MessageUserConnected message = new MessageUserConnected();
        message.setUsername(event.getUser().getName());
        ObjectMapper mapper = new ObjectMapper();
        for (Socket socket : connections.getConnections()) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(mapper.writeValueAsString(message));
        }
    }
}
