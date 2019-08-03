package ru.stoliarenko.gb.spring.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity(name = "app_user")
public class User implements Serializable {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "user_name")
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Project> projects;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
