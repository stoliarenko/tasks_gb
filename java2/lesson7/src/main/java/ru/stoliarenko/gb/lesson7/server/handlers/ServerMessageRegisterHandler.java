package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.model.MessageUserRegister;
import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.events.ConnectionAuthorizationEvent;
import ru.stoliarenko.gb.lesson7.server.events.ResponceMessageDamagedEvent;
import ru.stoliarenko.gb.lesson7.server.events.ResponceMessageRegisterEvent;
import ru.stoliarenko.gb.lesson7.server.events.ServerMessageRegisterEvent;
import ru.stoliarenko.gb.lesson7.server.services.UsersService;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ServerMessageRegisterHandler {
    @Inject
    private UsersService users;
    @Inject
    private MessageConverter converter;
    @Inject
    private Event<ResponceMessageDamagedEvent> messageDamagedEvent;
    @Inject
    private Event<ResponceMessageRegisterEvent> registerResponceEvent;
    @Inject
    private Event<ConnectionAuthorizationEvent> authorizationEvent;
    
    public void register(@Observes final ServerMessageRegisterEvent event) {
        try {
            final MessageUserRegister message = converter.convertToMessage(event.getText(), MessageUserRegister.class);
            final User user = new User();
            user.setLogin(message.getLogin());
            user.setName(message.getLogin());
            user.setPassword(message.getPassword());
            final boolean registred = users.register(user);
            if (registred) {
                authorizationEvent.fireAsync(new ConnectionAuthorizationEvent(event.getConnection(), user));
                registerResponceEvent.fire(new ResponceMessageRegisterEvent(event.getConnection(), true, ""));
            }
            registerResponceEvent.fire(new ResponceMessageRegisterEvent(event.getConnection(), false, "Invalid login or name"));
        } catch (Exception e) {
            messageDamagedEvent.fire(new ResponceMessageDamagedEvent(event.getConnection()));
        } 
    }
}
