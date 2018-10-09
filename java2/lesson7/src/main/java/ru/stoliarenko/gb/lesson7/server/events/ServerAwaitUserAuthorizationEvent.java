package ru.stoliarenko.gb.lesson7.server.events;

import lombok.AllArgsConstructor;
import ru.stoliarenko.gb.lesson7.model.Connection;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ServerAwaitUserAuthorizationEvent {
    private Connection connection;
}
