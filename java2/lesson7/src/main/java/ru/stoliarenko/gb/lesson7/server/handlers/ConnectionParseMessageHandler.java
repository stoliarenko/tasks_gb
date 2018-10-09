package ru.stoliarenko.gb.lesson7.server.handlers;

import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.Message;
import ru.stoliarenko.gb.lesson7.server.events.*;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ConnectionParseMessageHandler {
    @Inject
    private Event<ServerMessageLoginEvent> loginMessageEvent;
    @Inject
    private Event<ServerMessageLogoutEvent> logoutMessageEvent;
    @Inject
    private Event<ServerMessageRegisterEvent> registerMessageEvent;
    @Inject
    private Event<ServerMessageBroadcastEvent> broadcastMessageEvent;
    @Inject
    private Event<ServerMessagePrivateEvent> privateMessageEvent;
    @Inject
    private MessageConverter converter;
    
    @SneakyThrows
    public void parseMessage(@ObservesAsync final ConnectionParseMessageEvent event) {
        final Connection connection = event.getConnection();
        final String text = event.getText();
        
        final Message message = converter.convertToMessage(text, Message.class);
        
        switch (message.getType()) {
            case USER_LOGIN:
                loginMessageEvent.fireAsync(new ServerMessageLoginEvent(connection, text));
                break;
            case USER_LOGOUT:
                logoutMessageEvent.fireAsync(new ServerMessageLogoutEvent(connection));
                break;
            case USER_REGISTER:
                registerMessageEvent.fireAsync(new ServerMessageRegisterEvent(connection, text));
                break;
            case MESSAGE_BROADCAST:
                broadcastMessageEvent.fireAsync(new ServerMessageBroadcastEvent(connection, text));
                break;
            case MESSAGE_PRIVATE:
                privateMessageEvent.fireAsync(new ServerMessagePrivateEvent(connection, text));
                break;
        }
    }
}
