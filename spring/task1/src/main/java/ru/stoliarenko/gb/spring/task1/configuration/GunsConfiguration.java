package ru.stoliarenko.gb.spring.task1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.stoliarenko.gb.spring.task1.annotation.Heavy;
import ru.stoliarenko.gb.spring.task1.model.ammo.BoxOfBullets;
import ru.stoliarenko.gb.spring.task1.model.ammo.BoxOfShells;
import ru.stoliarenko.gb.spring.task1.model.api.Ammo;
import ru.stoliarenko.gb.spring.task1.model.gun.HeavyRevolver;
import ru.stoliarenko.gb.spring.task1.model.gun.Shotgun;

@Configuration
public class GunsConfiguration {

    @Heavy
    @Scope("singleton")
    @Bean("boxOfBullets")
    public BoxOfBullets getBoxOfBullets() {
        return new BoxOfBullets();
    }

    @Bean("revolver")
    @Scope("prototype")
    public HeavyRevolver getDisposableRevolver(@Heavy Ammo boxOfBullets) {
        final HeavyRevolver revolver = new HeavyRevolver();
        revolver.setBulletBox(boxOfBullets);
        return revolver;
    }

    public BoxOfShells getBoxOfShells() {
        return new BoxOfShells();
    }

    public Shotgun getPlainOldShotgun(BoxOfShells shellBox) {
        final Shotgun shotgun = new Shotgun();
        shotgun.setShellBox(shellBox);
        return shotgun;
    }

}
