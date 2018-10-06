package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class MessageUserRejected extends Message {
    private String cause;
    
    {
        setType(MessageType.USER_REJECTED);
    }
}
