package com.yourcompany.gym.model;

import jakarta.persistence.*;
import java.time.LocalDate; // <-- Новый, правильный импорт
import java.util.Set;

@Entity
@Table(name = "trainees")
public class Trainee extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth; // <-- Заменили тип

    @Column(name = "address")
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trainee_trainer", // Название промежуточной таблицы
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers;

    // Геттеры и сеттеры для dateOfBirth и address...
    public Set<Trainer> getTrainers() {
        return trainers;
    }
    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
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
}