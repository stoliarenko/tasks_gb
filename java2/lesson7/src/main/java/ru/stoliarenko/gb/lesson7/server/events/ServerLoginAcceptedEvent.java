package ru.stoliarenko.gb.lesson7.server.events;

import java.net.Socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.stoliarenko.gb.lesson7.model.User;

@Getter
@AllArgsConstructor
public final class ServerLoginAcceptedEvent {
    private final Socket socket;
    private final User user;
}
