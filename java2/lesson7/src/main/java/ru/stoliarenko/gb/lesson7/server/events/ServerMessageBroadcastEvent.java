package ru.stoliarenko.gb.lesson7.server.events;

import lombok.AllArgsConstructor;
import ru.stoliarenko.gb.lesson7.model.Message;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ServerMessageBroadcastEvent {
    private Message message;
}
