package com.yourcompany.gym.dto;

import com.yourcompany.gym.model.TrainingType;

public record TrainingTypeDTO(Long id, String name) {

    public static TrainingTypeDTO fromEntity(TrainingType entity) {
        return new TrainingTypeDTO(entity.getId(), entity.getTrainingTypeName());
    }
}