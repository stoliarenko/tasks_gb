package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class MessageUserLoginResponce extends Message {
    private boolean success;
    private String cause = "";
    {
        setType(MessageType.USER_LOGIN_RESPONCE);
    }
}
