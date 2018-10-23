package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.UserPrivateMessageEvent;
import ru.stoliarenko.gb.lesson7.model.MessageTextPrivate;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class UserPrivateMessageHandler {
    @Inject
    private Client client;
    @Inject
    private MessageConverter messageConverter;
    
    public void whisper(@ObservesAsync UserPrivateMessageEvent event) {
        final String inputText = event.getUserInput();
        if (inputText.split(" ").length < 3) return;
        final String targetUser = inputText.split(" ")[1];
        final int textPosition = targetUser.length() + inputText.split(targetUser)[0].length();
        
        final MessageTextPrivate message = new MessageTextPrivate();
        message.setUsername(targetUser);
        message.setText(inputText.substring(textPosition));
        
        client.getConnection().send(messageConverter.convertToString(message));
    }
}
