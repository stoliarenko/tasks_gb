package ru.stoliarenko.gb.spring.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "app_task")
public class Task implements Serializable {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private Project project;

    @Column(name = "task_name")
    private String name;

    @Column(name = "task_description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

}
