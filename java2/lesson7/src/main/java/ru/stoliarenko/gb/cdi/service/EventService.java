package ru.stoliarenko.gb.cdi.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import ru.stoliarenko.gb.cdi.events.SomeEvent;

@ApplicationScoped
public class EventService {
    @Inject
    Event<SomeEvent> someEvent;
    public void run() {
        someEvent.fire(new SomeEvent());
    }
}
