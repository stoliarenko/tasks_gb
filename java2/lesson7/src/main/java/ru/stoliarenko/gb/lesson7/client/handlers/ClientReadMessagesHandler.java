package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ClientParseMessagesEvent;
import ru.stoliarenko.gb.lesson7.client.events.ClientReadMessagesEvent;
import ru.stoliarenko.gb.lesson7.client.services.ClientLogger;

@ApplicationScoped
public final class ClientReadMessagesHandler {
    @Inject
    private Client client;
    @Inject
    private Event<ClientReadMessagesEvent> readMessagesEvent;
    @Inject
    private Event<ClientParseMessagesEvent> parseMessagesEvent;
    
    @SneakyThrows
    public void readMessages(@ObservesAsync ClientReadMessagesEvent event) {
        try {
            final String message = client.getConnection().receive();
            ClientLogger.writeMessage("Received message: " + message);
            parseMessagesEvent.fireAsync(new ClientParseMessagesEvent(message));
            readMessagesEvent.fireAsync(new ClientReadMessagesEvent());
        } catch (Exception e) {
            ClientLogger.writeMessage("Client disconnected.");
            client.getConnection().close();
        }
    }
}
