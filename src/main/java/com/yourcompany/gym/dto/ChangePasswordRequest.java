package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Old password cannot be blank")
        String oldPassword,

        @NotBlank(message = "New password cannot be blank")
        @Size(min = 8, message = "New password must be at least 8 characters long")
        String newPassword
) {}