package ru.stoliarenko.gb.lesson7.client.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ReceivedMessageBroadcastEvent {
    private String textMessage;
}
