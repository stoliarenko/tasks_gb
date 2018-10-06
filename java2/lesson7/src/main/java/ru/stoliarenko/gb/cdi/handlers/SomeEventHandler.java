package ru.stoliarenko.gb.cdi.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import ru.stoliarenko.gb.cdi.events.SomeEvent;

@ApplicationScoped
public class SomeEventHandler {
    public void handle(@Observes SomeEvent event) {
        System.out.println("SomeEventHandler!");
    }
}
