package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ReceivedMessageBroadcastEvent;
import ru.stoliarenko.gb.lesson7.model.MessageTextBroadcast;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

public final class ReceivedMessageBroadcastHandler {

    @Inject
    private Client client;
    @Inject
    private MessageConverter converter;
    
    public void receive(@Observes ReceivedMessageBroadcastEvent event) {
        final MessageTextBroadcast message = converter.convertToMessage(event.getTextMessage(), MessageTextBroadcast.class);
        client.getClientView().showMessage(message.getText()); //TODO добавить посредника
    }
}
