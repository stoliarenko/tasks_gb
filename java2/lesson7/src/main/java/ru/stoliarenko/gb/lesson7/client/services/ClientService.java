package ru.stoliarenko.gb.lesson7.client.services;

import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ClientParseUserInputEvent;
import ru.stoliarenko.gb.lesson7.client.events.ClientReadMessagesEvent;
import ru.stoliarenko.gb.lesson7.client.events.ClientReadUserInputEvent;
import ru.stoliarenko.gb.lesson7.client.view.PonyChatClient;
import ru.stoliarenko.gb.lesson7.config.Configuration;
import ru.stoliarenko.gb.lesson7.model.Connection;

@Getter @Setter
@ApplicationScoped
public class ClientService implements Client{
    private Connection connection;
    @Inject
    private PonyChatClient clientView;
    @Inject
    private Configuration configuration;
    @Inject
    private Event<ClientReadUserInputEvent> readInput;
    @Inject
    private Event<ClientReadMessagesEvent> receiveMessages;
    @Inject
    private Event<ClientParseUserInputEvent> parseUserInputEvent;
    
    @SneakyThrows
    public void run() {
        connection = new Connection(new Socket(configuration.getAddress(), configuration.getPort()));
        ClientLogger.writeMessage("Client connected");
        receiveMessages.fireAsync(new ClientReadMessagesEvent());
        readInput.fire(new ClientReadUserInputEvent());
    }
    
    public void proceedInput(final String text) {
        parseUserInputEvent.fireAsync(new ClientParseUserInputEvent(text));
    }
}
