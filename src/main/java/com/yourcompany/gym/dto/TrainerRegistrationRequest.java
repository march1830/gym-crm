package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TrainerRegistrationRequest(
        @NotBlank(message = "First name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only letters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only letters")
        String lastName,

        @NotNull(message = "Specialization ID cannot be null")
        Long specializationId
) {}
