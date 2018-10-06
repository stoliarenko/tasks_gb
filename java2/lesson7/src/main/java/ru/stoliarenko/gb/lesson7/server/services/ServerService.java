package ru.stoliarenko.gb.lesson7.server.services;

import java.net.ServerSocket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.config.Configuration;
import ru.stoliarenko.gb.lesson7.server.api.Server;
import ru.stoliarenko.gb.lesson7.server.events.NewServerConnectionEvent;

@Getter @Setter
@NoArgsConstructor
@ApplicationScoped
public class ServerService implements Server {
    private ServerSocket serverSocket;
    
    @Inject
    private Configuration configuration;
    @Inject
    Event<NewServerConnectionEvent> newConnectionEvent;
    
    @SneakyThrows
    public void run() {
        serverSocket = new ServerSocket(configuration.getPort());
        ServerLogger.writeMessage("Server started!");
        newConnectionEvent.fire(new NewServerConnectionEvent());
    }

}
