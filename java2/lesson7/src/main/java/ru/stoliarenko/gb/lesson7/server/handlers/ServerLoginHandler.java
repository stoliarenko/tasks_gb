package ru.stoliarenko.gb.lesson7.server.handlers;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.stoliarenko.gb.lesson7.model.MessageUserLogin;
import ru.stoliarenko.gb.lesson7.model.MessageUserRejected;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.MessageDamagedEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerLoginAcceptedEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerLoginEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerLoginRejectedEvent;
import ru.stoliarenko.gb.lesson7.server.services.ServerLogger;
import ru.stoliarenko.gb.lesson7.server.services.UsersService;

@ApplicationScoped
public final class ServerLoginHandler {
    @Inject
    private Event<MessageDamagedEvent> messageDamaged;
    @Inject
    private Event<ServerLoginRejectedEvent> loginRejected;
    @Inject
    private Event<ServerLoginAcceptedEvent> loginAccepted;
    @Inject
    private UsersService users;
    
    public void login(@ObservesAsync ServerLoginEvent event) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final MessageUserLogin loginMessage = mapper.readValue(event.getText(), MessageUserLogin.class);
            final User user = users.getByLogin(loginMessage.getLogin());
            if (user == null || !user.getPassword().equals(loginMessage.getPassword())) {
                final MessageUserRejected loginRejectedMessage = new MessageUserRejected();
                loginRejectedMessage.setCause(user==null? "No such user" : "Incorrect password");
                loginRejected.fireAsync(new ServerLoginRejectedEvent(event.getSocket(), loginRejectedMessage));
                return;
            }
            loginAccepted.fireAsync(new ServerLoginAcceptedEvent(event.getSocket(), user));
        } catch (Exception e) {
            messageDamaged.fireAsync(new MessageDamagedEvent(event.getSocket()));
        }
    }
}
