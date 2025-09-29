package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainee;

public record TraineeDTO(String firstName, String lastName, String username) {

    public static TraineeDTO fromEntity(Trainee trainee) {
        return new TraineeDTO(trainee.getFirstName(), trainee.getLastName(), trainee.getUsername());
    }
}