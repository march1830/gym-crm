package com.yourcompany.gym.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "trainers")
public class Trainer extends User {

    // <-- ID здесь больше не нужен! Он наследуется от User.

    @ManyToOne(fetch = FetchType.LAZY) // Указываем связь "многие к одному"
    @JoinColumn(name = "specialization_id") // По какой колонке будет связь
    private TrainingType specialization;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trainee_trainer", // Название промежуточной таблицы
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers;

    // Геттеры и сеттеры для specialization...

    public Set<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }
    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }
}
