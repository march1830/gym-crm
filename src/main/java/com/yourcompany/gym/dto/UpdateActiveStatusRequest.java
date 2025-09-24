package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotNull;

// This DTO is used to send a request to change the active status of a user.
public record UpdateActiveStatusRequest(
        @NotNull(message = "Active status cannot be null")
        Boolean isActive
) {}
