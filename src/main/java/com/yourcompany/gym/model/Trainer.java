package com.yourcompany.gym.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "trainers")
public class Trainer extends User {



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    private TrainingType specialization;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers;

    @ManyToMany(mappedBy = "trainers", fetch = FetchType.LAZY)
    private Set<Trainee> trainees;

    // Getter
    public Set<Trainee> getTrainees() {
        return trainees;
    }

    // Setter
    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }

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
