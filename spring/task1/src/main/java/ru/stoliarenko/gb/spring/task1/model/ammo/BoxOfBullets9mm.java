package ru.stoliarenko.gb.spring.task1.model.ammo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.stoliarenko.gb.spring.task1.annotation.Heavy;
import ru.stoliarenko.gb.spring.task1.annotation.Light;
import ru.stoliarenko.gb.spring.task1.model.AmmoType;
import ru.stoliarenko.gb.spring.task1.model.api.Ammo;

@Light
public class BoxOfBullets9mm implements Ammo {

    private int count = 32;

    @NotNull
    @Override
    public AmmoType getType() {
        return AmmoType.BULLET;
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
