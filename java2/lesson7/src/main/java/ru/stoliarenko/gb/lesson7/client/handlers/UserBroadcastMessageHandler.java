package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.UserBroadcastMessageEvent;
import ru.stoliarenko.gb.lesson7.model.MessageTextBroadcast;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class UserBroadcastMessageHandler {
    @Inject
    private Client client;
    @Inject
    private MessageConverter messageConverter;
    
    public void broadcast(@ObservesAsync UserBroadcastMessageEvent event) {
        final MessageTextBroadcast message = new MessageTextBroadcast();
        message.setText(event.getUserInput());
        
        client.getConnection().send(messageConverter.convertToString(message));
    }
}
