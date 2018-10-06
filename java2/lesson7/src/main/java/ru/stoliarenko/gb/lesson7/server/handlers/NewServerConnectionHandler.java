package ru.stoliarenko.gb.lesson7.server.handlers;

import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.server.api.Server;
import ru.stoliarenko.gb.lesson7.server.events.NewServerConnectionEvent;
import ru.stoliarenko.gb.lesson7.server.events.NewMessageEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;

@ApplicationScoped
public final class NewServerConnectionHandler {
    @Inject
    private Server server;
    @Inject
    private ConnectionsService connections;
    
    @Inject
    private Event<NewServerConnectionEvent> newConnectionEvent;
    @Inject 
    private Event<NewMessageEvent> readMessagesEvent;
    
    @SneakyThrows
    public void createConnection(@Observes NewServerConnectionEvent event) {
        final Socket newUserConnectionSocket = server.getServerSocket().accept();
        System.out.println("Established connection with " + newUserConnectionSocket.getRemoteSocketAddress());
        connections.addConnection(newUserConnectionSocket);
        
        readMessagesEvent.fireAsync(new NewMessageEvent(newUserConnectionSocket));
        newConnectionEvent.fire(new NewServerConnectionEvent());
    }
}
