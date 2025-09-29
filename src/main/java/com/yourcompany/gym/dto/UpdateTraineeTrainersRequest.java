package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UpdateTraineeTrainersRequest(
        @NotEmpty List<String> trainerUsernames
) {}
