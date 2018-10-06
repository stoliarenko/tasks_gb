package ru.stoliarenko.gb.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class MessageUserRegister extends Message {
    private String login;
    private String password;
    {
        setType(MessageType.USER_REGISTER);
    }
}
