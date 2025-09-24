package com.yourcompany.gym.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "trainers")
public class Trainer extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    // This defines a many-to-one relationship with TrainingType.
    // One TrainingType (e.g., 'Cardio') can have many trainers.
    private TrainingType specialization;

    @ManyToMany(mappedBy = "trainers", fetch = FetchType.LAZY)
    // This is the "inverse" or "mapped" side of the many-to-many relationship.
    // 'mappedBy = "trainers"' tells JPA: "The configuration for the join table
    // is defined in the 'Trainee' class, inside the field named 'trainers'".
    // We are just linking back to it here.
    private Set<Trainee> trainees;


    // --- Getters and Setters ---

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    public Set<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }
}
