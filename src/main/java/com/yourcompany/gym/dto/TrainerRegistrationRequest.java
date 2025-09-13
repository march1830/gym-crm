package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TrainerRegistrationRequest(
        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        @NotNull(message = "Specialization ID cannot be null")
        Long specializationId
) {}
