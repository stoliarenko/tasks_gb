package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.model.MessageType;
import ru.stoliarenko.gb.lesson7.model.MessageUserDisconnected;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageBroadcastEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerLogoutEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;

@ApplicationScoped
public final class ServerLogoutHandler {
    @Inject
    private Event<ServerMessageBroadcastEvent> broadcast;
    @Inject
    private ConnectionsService connections;
    @Inject
    private ServerLogger logger;

    public void logout(@ObservesAsync final ServerLogoutEvent event) {
        final User user = connections.getUser(event.getSocket());
        connections.logout(event.getSocket());
        if (user==null) return;
        
        logger.writeMessage(String.format("User %s disconnected.", user.getName()));
        final MessageUserDisconnected userLogoutMessage = new MessageUserDisconnected();
        userLogoutMessage.setType(MessageType.USER_DISCONNECTED);
        userLogoutMessage.setUsername(user.getName());
        broadcast.fireAsync(new ServerMessageBroadcastEvent(userLogoutMessage));
    }
}
