package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.model.MessageUserLogin;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionAuthorizationEvent;
import ru.stoliarenko.gb.lesson7.server.events.ResponceMessageDamagedEvent;
import ru.stoliarenko.gb.lesson7.server.events.ResponceMessageLoginEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageLoginEvent;
import ru.stoliarenko.gb.lesson7.server.services.UsersService;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ServerMessageLoginHandler {
    @Inject
    private MessageConverter converter;
    @Inject
    private UsersService users;
    @Inject
    private Event<ResponceMessageDamagedEvent> messageDamagedEvent;
    @Inject
    private Event<ResponceMessageLoginEvent> loginResponceEvent;
    @Inject
    private Event<ConnectionAuthorizationEvent> authorizationEvent;
    
    public void login(@Observes ServerMessageLoginEvent event) {
        try {
            final MessageUserLogin message = converter.convertToMessage(event.getText(), MessageUserLogin.class);
            final User user = users.getUser(message.getLogin(), message.getPassword());
            if (user == User.NULL_USER) {
                final String cause = "Invalid login+password combination.";
                loginResponceEvent.fire(new ResponceMessageLoginEvent(event.getConnection(), false, cause));
                return;
            }
            authorizationEvent.fireAsync(new ConnectionAuthorizationEvent(event.getConnection(), user));
            loginResponceEvent.fire(new ResponceMessageLoginEvent(event.getConnection(), true, user.getName()));
        } catch (Exception e) {
            messageDamagedEvent.fire(new ResponceMessageDamagedEvent(event.getConnection()));
        }
    }
}
