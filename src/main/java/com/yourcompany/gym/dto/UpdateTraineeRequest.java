package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

// This DTO contains the data required to update a trainee's profile.
public record UpdateTraineeRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        LocalDate dateOfBirth,
        String address,

        @NotNull(message = "Active status cannot be null")
        Boolean isActive
) {}
