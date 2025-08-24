package com.yourcompany.gym.model;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass // 1. Указываем, что это суперкласс для других сущностей

public abstract class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;


    // Геттеры и сеттеры
    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean isActive() {

        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
