package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ReceivedMessagePrivateEvent;
import ru.stoliarenko.gb.lesson7.model.MessageTextPrivate;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

public final class ReceivedMessagePrivateHandler {
    
    @Inject
    private Client client;
    @Inject
    private MessageConverter converter;
    
    public void receive(@Observes ReceivedMessagePrivateEvent event) {
        final MessageTextPrivate message = converter.convertToMessage(event.getTextMessage(), MessageTextPrivate.class);
        client.getClientView().showMessage(message.getText()); //TODO переделать через посредника с форматированием
    }
}
