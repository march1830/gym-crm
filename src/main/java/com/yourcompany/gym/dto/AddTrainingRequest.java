package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

// DTO for the add training request
public record AddTrainingRequest(
        @NotBlank String traineeUsername,
        @NotBlank String password,
        @NotBlank String trainerUsername,
        @NotBlank String trainingName,
        @NotNull LocalDate trainingDate,
        @Positive int trainingDuration
) {}