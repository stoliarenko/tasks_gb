package ru.stoliarenko.gb.lesson7.server.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import ru.stoliarenko.gb.lesson7.model.User;

@Transactional
@ApplicationScoped
public class UserDAO {
    @Inject
    private EntityManager em;
    
    public void create(User user) {
        em.persist(user);
    }
    
    public List<User> getUsers(){
        List<User> users = em.createQuery("SELECT e FROM User e", User.class).getResultList();
        System.out.println(users.size());
        return em.createQuery("SELECT e FROM User e", User.class).getResultList();
    }
    
    public void remove(String name) {
        final User user = em.find(User.class, name);
        em.remove(user);
    }
}
