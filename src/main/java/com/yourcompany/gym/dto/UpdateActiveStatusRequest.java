package com.yourcompany.gym.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateActiveStatusRequest(
        @NotNull(message = "Active status cannot be null")
        Boolean isActive
) {}
