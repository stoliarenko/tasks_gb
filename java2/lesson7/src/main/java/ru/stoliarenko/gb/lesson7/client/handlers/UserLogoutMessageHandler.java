package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.UserLogoutMessageEvent;
import ru.stoliarenko.gb.lesson7.model.MessageUserLogout;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class UserLogoutMessageHandler {
    @Inject
    private Client client;
    @Inject
    private MessageConverter messageConverter;
    
    public void logout(@ObservesAsync UserLogoutMessageEvent event) {
        client.getConnection().send(messageConverter.convertToString(new MessageUserLogout()));
    }
}
