package ru.stoliarenko.gb.lesson7.server.services;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import ru.stoliarenko.gb.lesson7.model.User;

@ApplicationScoped
public class UsersService {
    private final Set<User> users = new LinkedHashSet<>();
    public UsersService(){
        User admin = new User();
        admin.setAdmin(true);
        admin.setLogin("admin");
        admin.setName("Boris_The_Razor");
        admin.setPassword("123456");
        users.add(admin);
    }
    
    private boolean collapses(final User user) {
        for (User registredUser : users) {
            if (registredUser.getLogin().equals(user.getLogin())) return true;
            if (registredUser.getName().equals(user.getName())) return true;
        }
        return false;
    }
    public boolean register(final User user) {
        if (collapses(user)) return false;
        users.add(user);
        return true;
    }
    public User getByLogin(final String user) {
        for (User registredUser : users) {
            if (registredUser.getLogin().equals(user)) return registredUser;
        }
        return null;
    }
    public User getByName(final String username) {
        for (User registredUser : users) {
            if (registredUser.getName().toLowerCase().equals(username.toLowerCase())) return registredUser;
        }
        return null;
    }
}
