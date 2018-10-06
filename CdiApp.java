package ru.stoliarenko.gb.cdi;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

public class CdiApp {
    public static void main(String[] args) {
        SeContainerInitializer.newInstance().addPackages(CdiApp.class);
        CDI.current();
    }
}
