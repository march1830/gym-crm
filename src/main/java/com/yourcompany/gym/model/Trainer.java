package com.yourcompany.gym.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trainers")
public class Trainer extends User {

    // <-- ID здесь больше не нужен! Он наследуется от User.

    @ManyToOne(fetch = FetchType.LAZY) // Указываем связь "многие к одному"
    @JoinColumn(name = "specialization_id") // По какой колонке будет связь
    private TrainingType specialization;

    // Геттеры и сеттеры для specialization...
    public TrainingType getSpecialization() { return specialization; }
    public void setSpecialization() { this.specialization = specialization; }
}
