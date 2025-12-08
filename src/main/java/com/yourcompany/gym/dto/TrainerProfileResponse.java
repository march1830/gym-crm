package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.Trainer;

import java.util.List;
import java.util.stream.Collectors;


public record TrainerProfileResponse(
        String firstName,
        String lastName,
        String specialization,
        boolean isActive,
        List<TraineeInfo> trainees
) {
    public static TrainerProfileResponse fromEntity(Trainer trainer) {

        List<TraineeInfo> traineeInfos = trainer.getTrainees()
                .stream()
                .map(TraineeInfo::fromEntity)
                .collect(Collectors.toList());

        String specializationName = trainer.getSpecialization() != null
                ? trainer.getSpecialization().getTrainingTypeName()
                : null;

        return new TrainerProfileResponse(
                trainer.getFirstName(),
                trainer.getLastName(),
                specializationName,
                trainer.isActive(),
                traineeInfos
        );
    }
}
