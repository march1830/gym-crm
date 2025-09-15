package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Training;
import java.time.LocalDate;

// DTO for returning training information
public record TrainingResponseDTO(
        String trainingName,
        LocalDate trainingDate,
        String trainingType,
        int trainingDuration,
        String trainerName, // For trainee's list
        String traineeName  // For trainer's list
) {
    public static TrainingResponseDTO forTrainee(Training training) {
        String trainerName = training.getTrainer() != null ? training.getTrainer().getFirstName() : null;
        String trainingTypeName = training.getTrainingType() != null ? training.getTrainingType().getTrainingTypeName() : null;
        return new TrainingResponseDTO(training.getTrainingName(), training.getTrainingDate(), trainingTypeName, training.getTrainingDuration(), trainerName, null);
    }

    public static TrainingResponseDTO forTrainer(Training training) {
        String traineeName = training.getTrainee() != null ? training.getTrainee().getFirstName() : null;
        String trainingTypeName = training.getTrainingType() != null ? training.getTrainingType().getTrainingTypeName() : null;
        return new TrainingResponseDTO(training.getTrainingName(), training.getTrainingDate(), trainingTypeName, training.getTrainingDuration(), null, traineeName);
    }
}