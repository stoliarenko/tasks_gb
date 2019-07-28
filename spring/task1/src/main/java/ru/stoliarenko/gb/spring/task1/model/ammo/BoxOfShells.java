package ru.stoliarenko.gb.spring.task1.model.ammo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stoliarenko.gb.spring.task1.model.AmmoType;
import ru.stoliarenko.gb.spring.task1.model.api.Ammo;

public class BoxOfShells implements Ammo {

    private int count = 12;

    @NotNull
    @Override
    public AmmoType getType() {
        return AmmoType.SHELL;
    }

    @NotNull
    @Override
    public int getCount() {
        return count;
    }

    @NotNull
    @Override
    public int take(@Nullable final int amount) {
        if (amount < 1) return 0;
        final int amountToTake = amount > count ? count : amount;
        count = count - amountToTake;
        return amountToTake;
    }

}
