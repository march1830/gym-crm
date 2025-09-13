package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainee;

// This record holds basic information about a trainee.
public record TraineeInfo(
        String username,
        String firstName,
        String lastName
) {
    public static TraineeInfo fromEntity(Trainee trainee) {
        return new TraineeInfo(
                trainee.getUsername(),
                trainee.getFirstName(),
                trainee.getLastName()
        );
    }
}
