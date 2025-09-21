package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainer;


public record TrainerDTO(String firstName, String lastName, String username, String specialization) {


    public static TrainerDTO fromEntity(Trainer trainer) {
        String specializationName = trainer.getSpecialization() != null
                ? trainer.getSpecialization().getTrainingTypeName()
                : null;
        return new TrainerDTO(trainer.getFirstName(), trainer.getLastName(), trainer.getUsername(), specializationName);
    }
}
