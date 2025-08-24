package com.yourcompany.gym.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Trainee extends User { // <-- ГЛАВНОЕ ИЗМЕНЕНИЕ: наследуем User

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Уникальные поля для Trainee
    private LocalDate dateOfBirth;
    private String address;

    // Пустой конструктор для JPA
    public Trainee() {
    }

    // Геттеры и сеттеры для id, dateOfBirth, address
    // Геттеры и сеттеры для firstName, lastName и т.д. будут унаследованы от User


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Правильная реализация equals() и hashCode() для JPA-сущности
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()!= o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return id!= null && id.equals(trainee.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
