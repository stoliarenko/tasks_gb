package ru.stoliarenko.gb.lesson7.server.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import ru.stoliarenko.gb.lesson7.model.User;

@Transactional
@ApplicationScoped
public class UsersService {
    @Inject
    private UserDAO ud;
    private boolean isInitialized = false;
    private final Set<User> users = new LinkedHashSet<>();
    
    public UsersService(){
        System.out.println("UsersService constructor");
    }
    private void initialize() {
        final List<User> dbList = ud.getUsers();
        for (User user : dbList) {
            users.add(user);
            System.out.println(String.format("User{%s} added!", user.getName()));
            isInitialized = true;
        }
    }
    
    private boolean collapses(final User user) {
        if(!isInitialized)initialize();// TODO убрать костыль
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
        if(!isInitialized)initialize();// TODO убрать костыль
        for (User registredUser : users) {
            if (registredUser.getLogin().equals(userLogin) && 
                registredUser.getPassword().equals(userPassword)) 
                return registredUser;
        }
        return User.NULL_USER;
    }
    public User getByName(final String username) {
        if(!isInitialized)initialize();// TODO убрать костыль
        for (User registredUser : users) {
            if (registredUser.getName().toLowerCase().equals(username.toLowerCase())) return registredUser;
        }
        return User.NULL_USER;
    }
}
