package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ClientUserDisconnectedEvent;
import ru.stoliarenko.gb.lesson7.model.MessageUserDisconnected;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

public final class ClientUserDisconnectedHandler {
    
    @Inject
    private MessageConverter converter;
    @Inject
    private Client client;
    
    public void disconnect(@Observes ClientUserDisconnectedEvent event) {
        final MessageUserDisconnected message = converter.convertToMessage(event.getTextMessage(), MessageUserDisconnected.class);
        client.getClientView().deleteUser(message.getUsername());
    }
}
