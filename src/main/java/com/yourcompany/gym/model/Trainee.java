package com.yourcompany.gym.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity // <-- ДОБАВЛЕНО: Указываем, что это JPA-сущность
public class Trainee extends User {

    @Id // <-- ДОБАВЛЕНО: Указываем, что это поле - первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID будет генерироваться автоматически
    private Long id;

    private LocalDate dateOfBirth;
    private String address;

    // Пустой конструктор для JPA
    public Trainee() {
    }

    // Геттеры и сеттеры для id, dateOfBirth, address
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

    // <-- ДОБАВЛЕНО: Правильная реализация equals() и hashCode()
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
