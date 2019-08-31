package ru.stoliarenko.gb.spring.hibernate.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stoliarenko.gb.spring.hibernate.configuration.JpaConfiguration;
import ru.stoliarenko.gb.spring.hibernate.model.Task;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JpaConfiguration.class)
public class TaskDaoTest {

    @Autowired
    private TaskDAO taskDAO;

    @Test
    public void testTaskPersistAndGet() {
        final Task newTask = new Task();
        newTask.setName("taskname");
        taskDAO.save(newTask);

        final Task persistedTask = taskDAO.getTask(newTask.getId());
        assertNotNull(persistedTask);
        assertEquals(newTask.getName(), persistedTask.getName());
    }

    @Test
    public void testTaskPersistAndDelete() {
        final Task newTask = new Task();
        newTask.setName("taskname");
        taskDAO.save(newTask);

        final Task persistedTask = taskDAO.getTask(newTask.getId());
        assertNotNull(persistedTask);
        assertEquals(newTask.getName(), persistedTask.getName());

        taskDAO.delete(newTask.getId());
        assertNull(taskDAO.getTask(newTask.getId()));
    }

    @Test
    public void testTaskPersistAndGetAll() {
        final Task firstTask = new Task();
        firstTask.setName("first-task");
        taskDAO.save(firstTask);

        final Task secondTask = new Task();
        secondTask.setName("second-task");
        taskDAO.save(secondTask);

        final Map<String, Task> allTasks = new HashMap<>();
        taskDAO.getAll().forEach(u -> allTasks.put(u.getId(), u));
        assertTrue(allTasks.containsKey(firstTask.getId()));
        assertEquals(firstTask.getName(), allTasks.get(firstTask.getId()).getName());
        assertTrue(allTasks.containsKey(secondTask.getId()));
        assertEquals(secondTask.getName(), allTasks.get(secondTask.getId()).getName());
    }

}
