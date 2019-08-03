package ru.stoliarenko.gb.spring.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.stoliarenko.gb.spring.hibernate.model.Project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProjectDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Project getProject(final String id) {
        return entityManager.find(Project.class, id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Project> getAll() {
        return entityManager.createQuery("FROM Project", Project.class).getResultList();
    }

    @Transactional
    public void save(final Project project) {
        entityManager.merge(project);
    }

    @Transactional
    public void delete(final String projectId) {
        entityManager.remove(entityManager.find(Project.class, projectId));
    }

}
