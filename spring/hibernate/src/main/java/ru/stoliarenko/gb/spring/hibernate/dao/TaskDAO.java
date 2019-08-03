package ru.stoliarenko.gb.spring.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.stoliarenko.gb.spring.hibernate.model.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TaskDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Task getTask(final String id) {
        return entityManager.find(Task.class, id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getAll() {
        return entityManager.createQuery("FROM Task", Task.class).getResultList();
    }

    @Transactional
    public void save(final Task task) {
        entityManager.merge(task);
    }

    @Transactional
    public void delete(final String taskId) {
        entityManager.remove(entityManager.find(Task.class, taskId));
    }

}
