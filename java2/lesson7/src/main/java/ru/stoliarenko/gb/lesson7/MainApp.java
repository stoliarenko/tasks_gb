package ru.stoliarenko.gb.lesson7;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

import ru.stoliarenko.gb.lesson7.client.services.ClientService;
import ru.stoliarenko.gb.lesson7.server.services.ServerService;

public class MainApp {

    public static void main(String[] args) {
        getApp(args).run();
    }
    public static Runnable getApp(String[] args){
        SeContainerInitializer.newInstance().addPackages(MainApp.class).initialize();
        
        if(args.length != 0) 
            return CDI.current().select(ServerService.class).get();
        return CDI.current().select(ClientService.class).get();
    }

}
