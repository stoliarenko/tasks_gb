package ru.stoliarenko.gb.lesson7.config;

import javax.enterprise.context.ApplicationScoped;

import lombok.Getter;

@Getter
@ApplicationScoped
public class Configuration {
    private final int port = 3036;
    private final String address = "localhost";
}
