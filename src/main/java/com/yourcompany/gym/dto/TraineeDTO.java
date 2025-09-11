package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainee;

// A simple record to hold data. Getters are generated automatically.
public record TraineeDTO(String firstName, String lastName, String username) {
    // A static factory method to convert an Entity to a DTO
    public static TraineeDTO fromEntity(Trainee trainee) {
        return new TraineeDTO(trainee.getFirstName(), trainee.getLastName(), trainee.getUsername());
    }
}