package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

// DTO for updating a trainee's list of trainers
public record UpdateTraineeTrainersRequest(
        @NotEmpty List<String> trainerUsernames
) {}
