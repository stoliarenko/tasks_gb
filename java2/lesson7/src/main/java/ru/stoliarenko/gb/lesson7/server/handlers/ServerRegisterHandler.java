package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.stoliarenko.gb.lesson7.model.MessageUserRegister;
import ru.stoliarenko.gb.lesson7.model.MessageUserRejected;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.MessageDamagedEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerLoginAcceptedEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerLoginRejectedEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerRegisterEvent;
import ru.stoliarenko.gb.lesson7.server.services.UsersService;

@ApplicationScoped
public final class ServerRegisterHandler {
    @Inject
    private UsersService users;
    @Inject
    private Event<MessageDamagedEvent> messageDamaged;
    @Inject
    private Event<ServerLoginRejectedEvent> loginRejected;
    @Inject 
    private Event<ServerLoginAcceptedEvent> loginAccepted;
    
    public void register(@ObservesAsync final ServerRegisterEvent event) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final MessageUserRegister registerMessage = mapper.readValue(event.getText(), MessageUserRegister.class);
            final User user = new User();
            user.setLogin(registerMessage.getLogin());
            user.setName(registerMessage.getLogin());
            user.setPassword(registerMessage.getPassword());
            final boolean registred = users.register(user);
            if (!registred) {
                final MessageUserRejected loginRejectedMessage = new MessageUserRejected();
                loginRejectedMessage.setCause("Login is already in use");
                loginRejected.fireAsync(new ServerLoginRejectedEvent(event.getSocket(), loginRejectedMessage));
                return;
            }
            loginAccepted.fire(new ServerLoginAcceptedEvent(event.getSocket(), user));
        } catch (Exception e) {
            messageDamaged.fireAsync(new MessageDamagedEvent(event.getSocket()));
        } 
    }
}
