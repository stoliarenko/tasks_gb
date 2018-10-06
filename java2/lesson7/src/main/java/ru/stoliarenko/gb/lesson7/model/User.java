package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public static final User NULL_USER = new User();
    private boolean isAdmin = false;
    private String name;
    private String login;
    private String password;
}
