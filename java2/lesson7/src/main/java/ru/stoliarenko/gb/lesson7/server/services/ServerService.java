package ru.stoliarenko.gb.lesson7.server.services;

import java.net.ServerSocket;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.config.Configuration;
import ru.stoliarenko.gb.lesson7.server.api.Server;
import ru.stoliarenko.gb.lesson7.server.events.ServerAcceptConnectionEvent;

@Getter @Setter
@ApplicationScoped
public class ServerService implements Server {
    
    private ServerSocket serverSocket;
    private Logger logger;
    
    @Inject
    private Configuration configuration;
    @Inject
    Event<ServerAcceptConnectionEvent> newConnectionEvent;
    
    @SneakyThrows
    public void run() {
        logger = Logger.getLogger("Server Logger");
        serverSocket = new ServerSocket(configuration.getPort());
        logger.info("Server started!");
        newConnectionEvent.fire(new ServerAcceptConnectionEvent());
    }

}
