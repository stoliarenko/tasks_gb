package ru.stoliarenko.gb.spring.task1.model.human;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stoliarenko.gb.spring.task1.model.api.Gun;
import ru.stoliarenko.gb.spring.task1.model.api.Human;

import javax.xml.ws.soap.Addressing;

@Component
public class StrangeCowboy implements Human {

    @Autowired @Deprecated
    private Gun leftHandGun;
    @Autowired @Addressing
    private Gun rightHandGun;

    @Override
    public void perform() {
        System.out.println("Strange cowboy is shooting!");
        boolean leftShot = true;
        boolean rightShot = true;
        while (leftShot || rightShot) {
            System.out.println("Left hand:");
            leftShot = leftHandGun.shoot();
            System.out.println("Right hand:");
            rightShot = rightHandGun.shoot();
        }
        System.out.println("End shooting.\n\n");
    }
}
