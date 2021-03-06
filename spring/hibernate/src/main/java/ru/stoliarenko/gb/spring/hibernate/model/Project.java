package ru.stoliarenko.gb.spring.hibernate.model;

import ru.stoliarenko.gb.spring.hibernate.enumerated.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity(name = "app_project")
public class Project implements Serializable {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private User user;

    @Column(name = "project_name")
    private String name;

    @Column(name = "project_description")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
