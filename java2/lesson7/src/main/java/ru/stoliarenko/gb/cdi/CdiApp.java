package ru.stoliarenko.gb.cdi;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

import ru.stoliarenko.gb.cdi.service.EventService;

public class CdiApp {
    public static void main(String[] args) {
        SeContainerInitializer.newInstance().addPackages(CdiApp.class).initialize();
        CDI.current().select(EventService.class).get().run();
    }
}
