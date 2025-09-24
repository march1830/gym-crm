package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateTrainerRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "First name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only letters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only letters")
        String lastName,

        // The specializationId field has been removed to make it read-only.

        @NotNull(message = "Active status cannot be null")
        Boolean isActive
) {}