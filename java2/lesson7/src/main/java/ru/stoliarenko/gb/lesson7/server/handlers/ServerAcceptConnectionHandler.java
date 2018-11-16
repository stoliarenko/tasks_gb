package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.server.api.Server;
import ru.stoliarenko.gb.lesson7.server.events.ServerAcceptConnectionEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerAwaitUserAuthorizationEvent;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionReadMessageEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;

@ApplicationScoped
public final class ServerAcceptConnectionHandler {
    @Inject
    private Server server;
    @Inject
    private ConnectionsService connections;
    @Inject
    private Event<ServerAcceptConnectionEvent> acceptConnection;
    @Inject 
    private Event<ConnectionReadMessageEvent> readMessagesEvent;
    @Inject
    private Event<ServerAwaitUserAuthorizationEvent> awaitAuthorizationEvent;
    
    @SneakyThrows
    public void createConnection(@Observes ServerAcceptConnectionEvent event) {
        final Connection newUserConnection = new Connection(server.getServerSocket().accept());
        server.getLogger().info("Established connection with " + newUserConnection.getRemoteSocketAddress());
        connections.addConnection(newUserConnection);
        
        awaitAuthorizationEvent.fireAsync(new ServerAwaitUserAuthorizationEvent(newUserConnection));
        readMessagesEvent.fireAsync(new ConnectionReadMessageEvent(newUserConnection));
        acceptConnection.fire(new ServerAcceptConnectionEvent());
    }
}
