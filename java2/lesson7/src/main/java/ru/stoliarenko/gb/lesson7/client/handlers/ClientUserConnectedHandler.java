package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.events.ClientUserConnectedEvent;
import ru.stoliarenko.gb.lesson7.client.services.ClientService;
import ru.stoliarenko.gb.lesson7.model.MessageUserConnected;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

public final class ClientUserConnectedHandler {
    @Inject 
    private MessageConverter converter;
    @Inject
    private ClientService client;
    
    public void connectUser(@Observes ClientUserConnectedEvent event) {
        MessageUserConnected message = converter.convertToMessage(event.getTextMessage(), MessageUserConnected.class);
        client.getClientView().addUser(message.getUsername());
    }
}
