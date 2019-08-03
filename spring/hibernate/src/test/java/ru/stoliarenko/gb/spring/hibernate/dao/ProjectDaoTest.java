package ru.stoliarenko.gb.spring.hibernate.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stoliarenko.gb.spring.hibernate.configuration.JpaConfiguration;
import ru.stoliarenko.gb.spring.hibernate.model.Project;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JpaConfiguration.class)
public class ProjectDaoTest {

    @Autowired
    private ProjectDAO projectDAO;

    @Test
    public void testProjectPersistAndGet() {
        final Project newProject = new Project();
        newProject.setName("projectname");
        projectDAO.save(newProject);

        final Project persistedProject = projectDAO.getProject(newProject.getId());
        assertNotNull(persistedProject);
        assertEquals(newProject.getName(), persistedProject.getName());
    }

    @Test
    public void testProjectPersistAndDelete() {
        final Project newProject = new Project();
        newProject.setName("projectname");
        projectDAO.save(newProject);

        final Project persistedProject = projectDAO.getProject(newProject.getId());
        assertNotNull(persistedProject);
        assertEquals(newProject.getName(), persistedProject.getName());

        projectDAO.delete(newProject.getId());
        assertNull(projectDAO.getProject(newProject.getId()));
    }

    @Test
    public void testProjectPersistAndGetAll() {
        final Project firstProject = new Project();
        firstProject.setName("first-project");
        projectDAO.save(firstProject);

        final Project secondProject = new Project();
        secondProject.setName("second-project");
        projectDAO.save(secondProject);

        final Map<String, Project> allProjects = new HashMap<>();
        projectDAO.getAll().forEach(u -> allProjects.put(u.getId(), u));
        assertTrue(allProjects.containsKey(firstProject.getId()));
        assertEquals(firstProject.getName(), allProjects.get(firstProject.getId()).getName());
        assertTrue(allProjects.containsKey(secondProject.getId()));
        assertEquals(secondProject.getName(), allProjects.get(secondProject.getId()).getName());
    }

}
