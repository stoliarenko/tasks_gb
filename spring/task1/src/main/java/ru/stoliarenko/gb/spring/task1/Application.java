package ru.stoliarenko.gb.spring.task1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.stoliarenko.gb.spring.task1.model.ammo.BoxOfBullets;
import ru.stoliarenko.gb.spring.task1.model.ammo.BoxOfShells;
import ru.stoliarenko.gb.spring.task1.model.api.Ammo;
import ru.stoliarenko.gb.spring.task1.model.api.Gun;
import ru.stoliarenko.gb.spring.task1.model.gun.Shotgun;

public class Application {

    public static void main(String[] args) {
        final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        final Ammo shells = context.getBean(BoxOfShells.class,"shellBox");
        Gun shotgun = context.getBean(Shotgun.class, "shotgun");
        while (shotgun.shoot()) {
            shotgun = context.getBean(Shotgun.class, "shotgun");
            System.out.println(shells.getCount() + " shells left.");
        }
        System.out.println("End of shooting.\n\n");

        final Ammo bullets = context.getBean(BoxOfBullets.class, "boxOfBullets");
        while (bullets.getCount() > 0) {
            final Gun revolver = (Gun) context.getBean("revolver");
            System.out.println(bullets.getCount() + " bullest remaining in box.");
            revolver.shoot();
        }
        System.out.println("No more bullets in the box.\n\n");

        final Gun lightRevolver = context.getBean("lightRevolver", Gun.class);
        while (lightRevolver.shoot());
        System.out.println("End of shooting!");
    }

}
