package ru.stoliarenko.gb.lesson7.server.events;

import java.net.Socket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class NewMessageEvent {
    private final Socket socket;

}
