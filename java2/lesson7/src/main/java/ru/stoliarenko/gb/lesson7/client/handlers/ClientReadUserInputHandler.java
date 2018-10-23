package ru.stoliarenko.gb.lesson7.client.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.client.events.ClientParseUserInputEvent;
import ru.stoliarenko.gb.lesson7.client.events.ClientReadUserInputEvent;
import ru.stoliarenko.gb.lesson7.client.services.ClientLogger;

@ApplicationScoped
public final class ClientReadUserInputHandler {
    @Inject
    private Event<ClientReadUserInputEvent> readInput;
    @Inject
    private Event<ClientParseUserInputEvent> processInput;
    
    @SneakyThrows
    public void readInput(@Observes ClientReadUserInputEvent event) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final String userInput = reader.readLine();
        processInput.fireAsync(new ClientParseUserInputEvent(userInput));
        readInput.fire(new ClientReadUserInputEvent());
    }
}
