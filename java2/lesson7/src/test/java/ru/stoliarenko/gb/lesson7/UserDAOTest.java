package ru.stoliarenko.gb.lesson7;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.stoliarenko.gb.lesson7.model.User;
import ru.stoliarenko.gb.lesson7.server.services.UserDAO;

@RunWith(CdiTestRunner.class)
public class UserDAOTest {
    @Inject
    private UserDAO ud;
    
    @Test
    public void test() {
//        ud.create(new User("1", "1", "1"));
       
//        final List<User> users = ud.getUsers();
//        System.out.println(users);
        
        ud.remove("1");
    }
}
