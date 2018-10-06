package ru.stoliarenko.gb.lesson7.server.handlers;

import java.io.DataInputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.server.events.MessageContentParsingEvent;
import ru.stoliarenko.gb.lesson7.server.events.NewMessageEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;

@ApplicationScoped
public final class NewMessageHandler {
    @Inject
    private ConnectionsService connections;
    
    @Inject
    private Event<NewMessageEvent> newMessage;
    @Inject
    private Event<MessageContentParsingEvent> parseMessage;
    
    public void readMessage(@ObservesAsync final NewMessageEvent event) {
        try {
            DataInputStream in = new DataInputStream(event.getSocket().getInputStream());
            final String message = in.readUTF();
            ServerLogger.writeMessage("Received message:" + message);
            newMessage.fireAsync(new NewMessageEvent(event.getSocket())); //TODO check if will work
            parseMessage.fireAsync(new MessageContentParsingEvent(event.getSocket(), message));
        } catch (IOException e) {
            connections.removeConnection(event.getSocket());
            //TODO message user disconnected
        }
    }
}
