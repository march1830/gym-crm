package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record TraineeRegistrationRequest(
        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        LocalDate dateOfBirth,
        String address
) {}