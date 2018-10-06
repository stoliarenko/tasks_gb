package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class MessageUserLogin extends Message {
    private String login;
    private String password;

    {
        setType(MessageType.USER_LOGIN);
    }
}
