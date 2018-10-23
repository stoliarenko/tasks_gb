package ru.stoliarenko.gb.lesson7.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {
    public static final User NULL_USER = new User();
    @Id
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String login;
    
    @Column(nullable = false)
    private String password;
    
    @Override
    public String toString() {
        return name;
    }
}