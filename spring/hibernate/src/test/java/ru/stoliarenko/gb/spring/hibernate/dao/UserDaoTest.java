package ru.stoliarenko.gb.spring.hibernate.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stoliarenko.gb.spring.hibernate.configuration.JpaConfiguration;
import ru.stoliarenko.gb.spring.hibernate.model.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JpaConfiguration.class)
public class UserDaoTest {

    @Autowired
    private UserDAO dao;

    @Test
    public void testUserPersistAndGet() {
        final User newUser = new User();
        newUser.setName("username");
        dao.save(newUser);
        final User persistedUser = dao.getUser(newUser.getId());
        assertNotNull(persistedUser);
        assertEquals(newUser.getName(), persistedUser.getName());
    }

}
