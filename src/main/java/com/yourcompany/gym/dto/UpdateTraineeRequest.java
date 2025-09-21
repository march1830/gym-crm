package com.yourcompany.gym.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdateTraineeRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "First name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only letters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only letters")
        String lastName,

        @Past(message = "Date of birth must be in the past")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,

        String address,

        @NotNull(message = "Active status cannot be null")
        Boolean isActive
) {}
