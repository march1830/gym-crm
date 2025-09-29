package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainee;

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
