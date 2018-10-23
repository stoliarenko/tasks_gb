package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class MessageTextBroadcast extends Message {
    private String text;
    
    {
        setType(MessageType.MESSAGE_BROADCAST);
    }
}
