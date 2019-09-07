package ru.stoliarenko.gb.spring.mvc.entity;

import java.util.UUID;

public class Product {

    private String id = UUID.randomUUID().toString();
    private String title;
    private Long cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

}
