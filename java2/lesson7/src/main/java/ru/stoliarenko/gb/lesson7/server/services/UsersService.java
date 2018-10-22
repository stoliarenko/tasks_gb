package ru.stoliarenko.gb.lesson7.server.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ru.stoliarenko.gb.lesson7.model.User;

@ApplicationScoped
public class UsersService {
    @Inject
    private UserDAO ud;
    
    private final Set<User> users = new LinkedHashSet<>();
    public UsersService(){
        System.out.println("UsersService constructor");
        initialize();
    }
    private void initialize() {
        System.out.println("Initializing");
        List<User> dbList = ud.getUsers();
        System.out.println("DB list size is " + dbList.size());
        for (User user : dbList) {
            users.add(user);
            System.out.println(String.format("User{%s} added!", user.getName()));
        }
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
        ud.create(user);
        return true;
    }
    public User getUser(final String userLogin, final String userPassword) {
        for (User registredUser : users) {
            if (registredUser.getLogin().equals(userLogin) && 
                registredUser.getPassword().equals(userPassword)) 
                return registredUser;
        }
        return User.NULL_USER;
    }
    public User getByName(final String username) {
        for (User registredUser : users) {
            if (registredUser.getName().toLowerCase().equals(username.toLowerCase())) return registredUser;
        }
        return User.NULL_USER;
    }
}
