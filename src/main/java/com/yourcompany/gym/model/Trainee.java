package com.yourcompany.gym.model;

import jakarta.persistence.*;
import java.time.LocalDate; // <-- Новый, правильный импорт

@Entity
@Table(name = "trainees")
public class Trainee extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth; // <-- Заменили тип

    @Column(name = "address")
    private String address;

    // Геттеры и сеттеры для dateOfBirth и address...
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
}