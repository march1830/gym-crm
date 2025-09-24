package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record TraineeRegistrationRequest(
        @NotBlank(message = "First name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only letters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only letters")
        String lastName,

        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        String address
) {}