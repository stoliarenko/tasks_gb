package ru.stoliarenko.gb.lesson7.server.handlers;

import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Message;
import ru.stoliarenko.gb.lesson7.server.events.*;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;

@ApplicationScoped
public final class MessageContentParser {
    @Inject
    private Event<ServerLoginEvent> serverLogin;
    @Inject
    private Event<ServerLogoutEvent> serverLogout;
    @Inject
    private Event<ServerRegisterEvent> serverRegister;
    @Inject
    private Event<ServerTextBroadcastEvent> broadcast;
    @Inject
    private Event<ServerSendPrivateEvent> sendPrivate;
    
    @SneakyThrows
    public void parseMessage(@ObservesAsync final MessageContentParsingEvent event) {
        final Socket socket = event.getSocket();
        final String text = event.getText();
        
        final ObjectMapper mapper = new ObjectMapper();
        final Message message = mapper.readValue(text, Message.class);
        
        switch (message.getType()) {
            case USER_LOGIN:
                serverLogin.fireAsync(new ServerLoginEvent(socket, text));
                break;
            case USER_LOGOUT:
                serverLogout.fireAsync(new ServerLogoutEvent(socket));
                break;
            case USER_REGISTER:
                serverRegister.fireAsync(new ServerRegisterEvent(socket, text));
                break;
            case MESSAGE_BROADCAST:
                broadcast.fireAsync(new ServerTextBroadcastEvent(socket, text));
                break;
            case MESSAGE_PRIVATE:
                sendPrivate.fireAsync(new ServerSendPrivateEvent(socket, text));
                break;
        }
    }
}
