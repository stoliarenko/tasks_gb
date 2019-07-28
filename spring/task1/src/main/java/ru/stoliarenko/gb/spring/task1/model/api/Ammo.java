package ru.stoliarenko.gb.spring.task1.model.api;

import org.jetbrains.annotations.NotNull;
import ru.stoliarenko.gb.spring.task1.model.AmmoType;

public interface Ammo {

    @NotNull
    AmmoType getType();

    int getCount();

    int take(int amount);

}
