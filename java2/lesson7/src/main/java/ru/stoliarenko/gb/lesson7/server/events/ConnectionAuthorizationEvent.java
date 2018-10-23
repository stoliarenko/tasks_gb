package ru.stoliarenko.gb.lesson7.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.stoliarenko.gb.lesson7.model.Connection;
import ru.stoliarenko.gb.lesson7.model.User;

@Getter
@AllArgsConstructor
public final class ConnectionAuthorizationEvent {
    private Connection connection;
    private User user;
}
