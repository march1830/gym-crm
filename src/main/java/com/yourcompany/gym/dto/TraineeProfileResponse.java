package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainee;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// This DTO represents the full profile of a trainee for a GET request.
public record TraineeProfileResponse(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String address,
        boolean isActive,
        List<TrainerInfo> trainers
) {
    public static TraineeProfileResponse fromEntity(Trainee trainee) {
        // Convert the Set<Trainer> entities into a List<TrainerInfo> DTOs
        List<TrainerInfo> trainerInfos = trainee.getTrainers()
                .stream()
                .map(TrainerInfo::fromEntity)
                .collect(Collectors.toList());

        return new TraineeProfileResponse(
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                trainee.isActive(),
                trainerInfos
        );
    }
}
