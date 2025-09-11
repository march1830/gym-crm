package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainer;

// Это DTO для Тренера. Обрати внимание, как мы получаем specializationName.
public record TrainerDTO(String firstName, String lastName, String username, String specialization) {

    // Этот статический метод будет превращать Entity в DTO.
    public static TrainerDTO fromEntity(Trainer trainer) {
        String specializationName = trainer.getSpecialization() != null
                ? trainer.getSpecialization().getTrainingTypeName()
                : null;
        return new TrainerDTO(trainer.getFirstName(), trainer.getLastName(), trainer.getUsername(), specializationName);
    }
}
