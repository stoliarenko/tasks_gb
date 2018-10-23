package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.UserRegisterMessageEvent;
import ru.stoliarenko.gb.lesson7.model.MessageUserRegister;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class UserRegisterMessageHandler {
    @Inject
    private Client client;
    @Inject
    private MessageConverter messageConverter;
    
    public void register(@ObservesAsync UserRegisterMessageEvent event) {
        final String inputText = event.getUserInput();
        final String[] inputWords = inputText.split(" ");
        if (inputWords.length == 4) {
            final MessageUserRegister message = new MessageUserRegister();
            message.setLogin(inputWords[1]);
            message.setPassword(inputWords[2]);
            message.setName(inputWords[3]);
            client.getConnection().send(messageConverter.convertToString(message));
            return;
        }
        //TODO Console separate request
    }
}
