package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

// DTO for the add training request
public record AddTrainingRequest(
        @NotBlank String traineeUsername,
        // The 'password' field has been removed.
        // Authentication is now handled by Spring Security via HttpBasic.
        @NotBlank String trainerUsername,
        @NotBlank String trainingName,
        @FutureOrPresent(message = "Training date must be today or in the future")
        @NotNull LocalDate trainingDate,
        @Positive int trainingDuration
) {}