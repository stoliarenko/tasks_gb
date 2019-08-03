package ru.stoliarenko.gb.spring.task1.model.gun;

import org.springframework.beans.factory.annotation.Autowired;
import ru.stoliarenko.gb.spring.task1.model.AmmoType;
import ru.stoliarenko.gb.spring.task1.model.api.Ammo;
import ru.stoliarenko.gb.spring.task1.model.api.Gun;

@Deprecated
public class DeprecatedRevolver implements Gun {

    private static final int BARREL_SIZE = 5;

    private int loadedBulletsCount = 0;

    private Ammo bulletBox;
    @Autowired @Deprecated
    public void setBulletBox(Ammo ammo) {
        this.bulletBox = ammo;
    }

    @Override
    public boolean reload() {
        if (bulletBox == null) {
            System.out.println("Shells are not supplied!");
            return false;
        }
        if (bulletBox.getType() != AmmoType.BULLET) {
            System.out.println("Wrong ammo tye, can't shoot!");
            return false;
        }
        final int totalShellCount = bulletBox.getCount();
        if (totalShellCount == 0) {
            System.out.println("Zero bullets available.");
            return false;
        }
        System.out.println("Reloading deprecated gun");
        final int bulletsToTake = BARREL_SIZE - loadedBulletsCount;
        if (bulletsToTake < 1) {
            System.out.println("No need to load, barrel is full");
            return true;
        }
        loadedBulletsCount = loadedBulletsCount + bulletBox.take(bulletsToTake);
        return true;
    }

    @Override
    public boolean shoot() {
        if (loadedBulletsCount < 1) {
            final boolean hasAmmo = reload();
            if (!hasAmmo) return false;
        }
        System.out.println("Deprecated-Bang!");
        loadedBulletsCount--;
        return true;
    }
}
