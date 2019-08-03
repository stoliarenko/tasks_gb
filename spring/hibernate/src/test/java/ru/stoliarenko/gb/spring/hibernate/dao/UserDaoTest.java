package ru.stoliarenko.gb.spring.hibernate.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stoliarenko.gb.spring.hibernate.configuration.JpaConfiguration;
import ru.stoliarenko.gb.spring.hibernate.model.User;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JpaConfiguration.class)
public class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testUserPersistAndGet() {
        final User newUser = new User();
        newUser.setName("username");
        userDAO.save(newUser);

        final User persistedUser = userDAO.getUser(newUser.getId());
        assertNotNull(persistedUser);
        assertEquals(newUser.getName(), persistedUser.getName());
    }

    @Test
    public void testUserPersistAndDelete() {
        final User newUser = new User();
        newUser.setName("username");
        userDAO.save(newUser);

        final User persistedUser = userDAO.getUser(newUser.getId());
        assertNotNull(persistedUser);
        assertEquals(newUser.getName(), persistedUser.getName());

        userDAO.delete(newUser.getId());
        assertNull(userDAO.getUser(newUser.getId()));
    }

    @Test
    public void testUserPersistAndGetAll() {
        final User firstUser = new User();
        firstUser.setName("first-user");
        userDAO.save(firstUser);

        final User secondUser = new User();
        secondUser.setName("second-user");
        userDAO.save(secondUser);

        final Map<String, User> allUsers = new HashMap<>();
        userDAO.getAll().forEach(u -> allUsers.put(u.getId(), u));
        assertTrue(allUsers.containsKey(firstUser.getId()));
        assertEquals(firstUser.getName(), allUsers.get(firstUser.getId()).getName());
        assertTrue(allUsers.containsKey(secondUser.getId()));
        assertEquals(secondUser.getName(), allUsers.get(secondUser.getId()).getName());
    }

}
