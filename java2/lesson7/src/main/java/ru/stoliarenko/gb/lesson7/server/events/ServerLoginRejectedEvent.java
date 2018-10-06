package ru.stoliarenko.gb.lesson7.server.events;

import java.net.Socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.stoliarenko.gb.lesson7.model.Message;

@Getter
@AllArgsConstructor
public final class ServerLoginRejectedEvent {
    private final Socket socket;
    private final Message message;
}
