package ru.stoliarenko.gb.lesson7;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

import ru.stoliarenko.gb.lesson7.client.services.ClientService;
import ru.stoliarenko.gb.lesson7.server.services.ServerService;

public class StartServer {

    public static void main(String[] args) {
        SeContainerInitializer.newInstance().addPackages(MainApp.class).initialize();
        CDI.current().select(ServerService.class).get().run();
    }

}
