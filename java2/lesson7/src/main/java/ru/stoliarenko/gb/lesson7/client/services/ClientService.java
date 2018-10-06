package ru.stoliarenko.gb.lesson7.client.services;

import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.client.api.Client;
import ru.stoliarenko.gb.lesson7.client.events.ClientReadMessagesEvent;
import ru.stoliarenko.gb.lesson7.client.events.ReadUserInputEvent;
import ru.stoliarenko.gb.lesson7.config.Configuration;

@Getter @Setter
@ApplicationScoped
public class ClientService implements Client{
    private Socket socket;
    @Inject
    private Configuration configuration;
    @Inject
    private Event<ReadUserInputEvent> readInput;
    @Inject
    private Event<ClientReadMessagesEvent> receiveMessages;
    
    @SneakyThrows
    public void run() {
        socket = new Socket(configuration.getAddress(), configuration.getPort());
        ClientLogger.writeMessage("Client connected");
        receiveMessages.fireAsync(new ClientReadMessagesEvent());
        readInput.fire(new ReadUserInputEvent());
    }
}
