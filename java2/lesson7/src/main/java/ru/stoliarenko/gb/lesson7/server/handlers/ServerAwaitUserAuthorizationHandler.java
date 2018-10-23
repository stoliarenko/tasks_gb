package ru.stoliarenko.gb.lesson7.server.handlers;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import lombok.SneakyThrows;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ServerAwaitUserAuthorizationEvent;
import ru.stoliarenko.gb.lesson7.server.services.ConnectionsService;

@ApplicationScoped
public final class ServerAwaitUserAuthorizationHandler {
    @Inject
    private ConnectionsService connections;
    
    @SneakyThrows
    public void await(@ObservesAsync ServerAwaitUserAuthorizationEvent event) {
        Thread.sleep(TimeUnit.MINUTES.toMillis(2));
        final Connection connection = event.getConnection();
        if (connections.getUser(connection) == User.NULL_USER) connection.close();
    }
}
