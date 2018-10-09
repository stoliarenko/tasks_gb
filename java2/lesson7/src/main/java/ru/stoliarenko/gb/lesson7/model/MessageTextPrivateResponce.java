package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class MessageTextPrivateResponce extends Message{
    private boolean success;
    private String description;
    {
        setType(MessageType.MESSAGE_PRIVATE_RESPONCE);
    }
}
