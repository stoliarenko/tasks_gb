package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.UserLoginMessageEvent;
import ru.stoliarenko.gb.lesson7.model.MessageUserLogin;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class UserLoginMessageHandler {
    @Inject
    private Client client;
    @Inject
    private MessageConverter messageConverter;
    
    public void login(@ObservesAsync UserLoginMessageEvent event) {
        final String userInput = event.getUserInput();
        final String[] inputWords = userInput.split(" ");
        if (inputWords.length == 3) {
            final MessageUserLogin message = new MessageUserLogin();
            message.setLogin(inputWords[1]);
            message.setPassword(inputWords[2]);
            client.getConnection().send(messageConverter.convertToString(message));
            return;
        }
        //TODO Console separate request
    }
}
