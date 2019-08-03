package ru.stoliarenko.gb.spring.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.stoliarenko.gb.spring.hibernate.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getUser(final String id) {
        return entityManager.find(User.class, id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> getAll() {
        return entityManager.createQuery("FROM app_user", User.class).getResultList();
    }

    @Transactional
    public void save(final User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void delete(final String userId) {
        entityManager.remove(entityManager.find(User.class, userId));
    }

}
