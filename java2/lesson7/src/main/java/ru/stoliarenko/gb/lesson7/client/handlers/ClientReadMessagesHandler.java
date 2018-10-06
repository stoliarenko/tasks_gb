package ru.stoliarenko.gb.lesson7.client.handlers;

import java.io.DataInputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ClientReadMessagesEvent;
import ru.stoliarenko.gb.lesson7.client.services.ClientLogger;

@ApplicationScoped
public final class ClientReadMessagesHandler {
    @Inject
    private Client client;
    @Inject
    private Event<ClientReadMessagesEvent> readMessages;
    
    @SneakyThrows
    public void readMessages(@ObservesAsync ClientReadMessagesEvent event) {
        final DataInputStream in = new DataInputStream(client.getSocket().getInputStream());
        final String message = in.readUTF();
        ClientLogger.writeMessage("Received message: " + message);
        readMessages.fireAsync(new ClientReadMessagesEvent());
    }
}
