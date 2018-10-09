package ru.stoliarenko.gb.lesson7.server.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.MessageUserLoginResponce;
import ru.stoliarenko.gb.lesson7.server.events.ResponceMessageLoginEvent;
import ru.stoliarenko.gb.lesson7.service.MessageConverter;

@ApplicationScoped
public final class ResponceMessageLoginHandler {
    @Inject
    private MessageConverter converter;
    
    public void loginResponce(@ObservesAsync ResponceMessageLoginEvent event) {
        final Connection connection = event.getConnection();
        final MessageUserLoginResponce message = new MessageUserLoginResponce();
        message.setSuccess(event.isSuccess());
        message.setCause(event.getCause());
        connection.send(converter.convertToString(message));
    }
}
