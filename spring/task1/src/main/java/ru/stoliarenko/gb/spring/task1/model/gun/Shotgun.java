package ru.stoliarenko.gb.spring.task1.model.gun;

import org.jetbrains.annotations.Nullable;
import ru.stoliarenko.gb.spring.task1.model.AmmoType;
import ru.stoliarenko.gb.spring.task1.model.api.Ammo;
import ru.stoliarenko.gb.spring.task1.model.api.Gun;

public class Shotgun implements Gun {

    private static final int BARREL_COUNT = 2;

    private int loadedShellsCount = 0;

    @Nullable
    private Ammo shellBox;

    public void setShellBox(@Nullable final Ammo shellBox) {
        this.shellBox = shellBox;
    }

    @Override
    public boolean reload() {
        if (shellBox == null) {
            System.out.println("Shells are not supplied!");
            return false;
        }
        if (shellBox.getType() != AmmoType.SHELL) {
            System.out.println("Wrong ammo tye, can't shoot!");
            return false;
        }
        final int totalShellCount = shellBox.getCount();
        if (totalShellCount == 0) {
            System.out.println("Zero shells available.");
            return false;
        }
        final int shellsToTake = BARREL_COUNT - loadedShellsCount;
        if (shellsToTake < 1) {
            System.out.println("No need to load, every barrel is loaded");
            return true;
        }
        loadedShellsCount = shellBox.take(shellsToTake);
        return true;
    }

    @Override
    public boolean shoot() {
        if (loadedShellsCount < 1) {
            final boolean hasAmmo = reload();
            if (!hasAmmo) return false;
        }
        if (loadedShellsCount > 1) System.out.print("piff-");
        System.out.println("paff!");
        loadedShellsCount = 0;
        return true;
    }

}
