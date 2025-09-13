package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// This DTO contains the data required to update a trainer's profile.
public record UpdateTrainerRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        @NotNull(message = "Specialization ID cannot be null")
        Long specializationId,

        @NotNull(message = "Active status cannot be null")
        Boolean isActive
) {}