package ru.stoliarenko.gb.lesson7.client.handlers;

import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.client.events.ClientParseMessagesEvent;
import ru.stoliarenko.gb.lesson7.client.events.ClientUserConnectedEvent;
import ru.stoliarenko.gb.lesson7.client.events.ClientUserDisconnectedEvent;
import ru.stoliarenko.gb.lesson7.client.events.ReceivedMessageBroadcastEvent;
import ru.stoliarenko.gb.lesson7.client.events.ReceivedMessagePrivateEvent;
import ru.stoliarenko.gb.lesson7.model.Message;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

public final class ClientParseMessagesHandler {
    
    @Inject
    private MessageConverter converter;
    @Inject
    private Event<ClientUserConnectedEvent> userConnectedEvent;
    @Inject
    private Event<ClientUserDisconnectedEvent> userDisconnectedEvent;
    @Inject
    private Event<ReceivedMessagePrivateEvent> receivedPrivateEvent;
    @Inject
    private Event<ReceivedMessageBroadcastEvent> receivedBroadcastEvent;
    

    public void parse(@ObservesAsync ClientParseMessagesEvent event) {
        final Message rawMessage = converter.convertToMessage(event.getTextMessage(), Message.class);
        
        switch(rawMessage.getType()) {
            case USER_CONNECTED: {
                userConnectedEvent.fire(new ClientUserConnectedEvent(event.getTextMessage()));
                break;
            }
            case USER_DISCONNECTED: {
                userDisconnectedEvent.fire(new ClientUserDisconnectedEvent(event.getTextMessage()));
                break;
            }
            case MESSAGE_PRIVATE: {
                receivedPrivateEvent.fire(new ReceivedMessagePrivateEvent(event.getTextMessage()));
                break;
            }
            case MESSAGE_BROADCAST: {
                receivedBroadcastEvent.fire(new ReceivedMessageBroadcastEvent(event.getTextMessage()));
                break;
            }
        }
    }
}
