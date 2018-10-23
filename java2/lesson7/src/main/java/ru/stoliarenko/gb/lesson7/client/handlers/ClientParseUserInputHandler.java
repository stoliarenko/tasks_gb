package ru.stoliarenko.gb.lesson7.client.handlers;

import java.io.DataOutputStream;
import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ClientParseUserInputEvent;
import ru.stoliarenko.gb.lesson7.client.events.UserBroadcastMessageEvent;
import ru.stoliarenko.gb.lesson7.client.events.UserLoginMessageEvent;
import ru.stoliarenko.gb.lesson7.client.events.UserLogoutMessageEvent;
import ru.stoliarenko.gb.lesson7.client.events.UserPrivateMessageEvent;
import ru.stoliarenko.gb.lesson7.client.events.UserRegisterMessageEvent;
import ru.stoliarenko.gb.lesson7.client.services.ClientLogger;
import ru.stoliarenko.gb.lesson7.model.MessageTextBroadcast;
import ru.stoliarenko.gb.lesson7.model.MessageTextPrivate;
import ru.stoliarenko.gb.lesson7.model.MessageUserLogin;

@ApplicationScoped
public final class ClientParseUserInputHandler {
    @Inject
    private Client client;
    @Inject
    private Event<UserLoginMessageEvent> userLoginEvent;
    @Inject
    private Event<UserLogoutMessageEvent> userLogoutEvent;
    @Inject
    private Event<UserRegisterMessageEvent> userRegisterEvent;
    @Inject
    private Event<UserPrivateMessageEvent> userPrivateMessageEvent;
    @Inject
    private Event<UserBroadcastMessageEvent> userBroadcastMessageEvent;
    
    private static final String LOGIN = "/login";
    private static final String LOGOUT = "/logout";
    private static final String REGISTER = "/register";
    private static final String WHISPER = "/w";
    
    @SneakyThrows
    public void processUserInput(@ObservesAsync ClientParseUserInputEvent event) {
        final String userInput = event.getText();
        if (userInput == null) return;
        
        if (userInput.startsWith(LOGIN)) {
            userLoginEvent.fireAsync(new UserLoginMessageEvent(userInput));
            return;
        }
        
        if (userInput.startsWith(LOGOUT)) {
            userLogoutEvent.fireAsync(new UserLogoutMessageEvent());
            return;
        }
        
        if (userInput.startsWith(REGISTER)) {
            userRegisterEvent.fireAsync(new UserRegisterMessageEvent(userInput));
            return;
        }
        
        if (userInput.startsWith(WHISPER)) {
            userPrivateMessageEvent.fireAsync(new UserPrivateMessageEvent(userInput));
            return;
        }
        
        userBroadcastMessageEvent.fireAsync(new UserBroadcastMessageEvent(userInput));
    }
}
