package ru.stoliarenko.gb.lesson6;
/**
 * Сущность пользователя - на данном этапе содержит одно поле
 * Нужен для масштабирования
 * 
 * @author Stoliarenko Alexander
 */
public class User {
    private static volatile int unnamedUserCounter = 0;
    private String name;
    
    public User(String name) {
        this.name = name;
    }
    public User() {
        this.name = "UnnamedUser#" + ++unnamedUserCounter;
    }
   
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return (name == null) ? 0 : name.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return name.equals(other.name);
    }
   
    @Override
    public String toString() {
        return name;
    }
}
