package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class MessageTextPrivate extends Message {
    private String text;
    private String username;
    
    {
        setType(MessageType.MESSAGE_PRIVATE);
    }
}
