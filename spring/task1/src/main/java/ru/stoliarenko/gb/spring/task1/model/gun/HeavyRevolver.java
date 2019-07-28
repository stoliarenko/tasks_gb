package ru.stoliarenko.gb.spring.task1.model.gun;

import ru.stoliarenko.gb.spring.task1.model.AmmoType;
import ru.stoliarenko.gb.spring.task1.model.api.Ammo;
import ru.stoliarenko.gb.spring.task1.model.api.Gun;

public class HeavyRevolver implements Gun {

    private static final int BARREL_SIZE = 6;

    private int loadedBulletsCount = 0;

    private Ammo bulletBox;

    public void setBulletBox(Ammo bulletBox) {
        this.bulletBox = bulletBox;
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
            System.out.println("Zero shellBox available.");
            return false;
        }
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
        reload();
        System.out.println("It doesnt shoot, but you always can use it as a blunt.");
        return false;
    }
}
