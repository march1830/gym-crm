package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Training;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public record TrainingResponseDTO(
        String trainingName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate trainingDate,
        String trainingType,
        int trainingDuration,
        String trainerName,
        String traineeName
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