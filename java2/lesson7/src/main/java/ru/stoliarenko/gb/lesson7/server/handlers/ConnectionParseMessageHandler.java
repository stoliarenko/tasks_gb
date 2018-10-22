package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.Message;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionParseMessageEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageBroadcastEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageLoginEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageLogoutEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessagePrivateEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageRegisterEvent;
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
    public void parseMessage(@Observes final ConnectionParseMessageEvent event) {
        final Connection connection = event.getConnection();
        final String text = event.getText();
        
        final Message message = converter.convertToMessage(text, Message.class);
        
        switch (message.getType()) {
            case USER_LOGIN:
                loginMessageEvent.fire(new ServerMessageLoginEvent(connection, text));
                break;
            case USER_LOGOUT:
                logoutMessageEvent.fire(new ServerMessageLogoutEvent(connection));
                break;
            case USER_REGISTER:
                registerMessageEvent.fire(new ServerMessageRegisterEvent(connection, text));
                break;
            case MESSAGE_BROADCAST:
                broadcastMessageEvent.fire(new ServerMessageBroadcastEvent(connection, text));
                break;
            case MESSAGE_PRIVATE:
                privateMessageEvent.fire(new ServerMessagePrivateEvent(connection, text));
                break;
        }
    }
}
