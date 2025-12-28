package com.yourcompany.gym.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TrainerWorkloadRequest {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Boolean isActive;
    private LocalDate trainingDate;
    private Long trainingDuration;
    private String actionType;

}
