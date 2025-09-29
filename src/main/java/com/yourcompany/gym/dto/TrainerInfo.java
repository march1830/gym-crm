package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainer;

public record TrainerInfo(
        String username,
        String firstName,
        String lastName,
        String specialization
) {
    public static TrainerInfo fromEntity(Trainer trainer) {
        String specializationName = trainer.getSpecialization() != null
                ? trainer.getSpecialization().getTrainingTypeName()
                : null;
        return new TrainerInfo(
                trainer.getUsername(),
                trainer.getFirstName(),
                trainer.getLastName(),
                specializationName
        );
    }
}
