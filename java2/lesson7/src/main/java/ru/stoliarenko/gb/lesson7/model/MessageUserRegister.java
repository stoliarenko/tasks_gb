package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class MessageUserRegister extends Message {
    private String login;
    private String password;
    private String name;
    {
        setType(MessageType.USER_REGISTER);
    }
}
