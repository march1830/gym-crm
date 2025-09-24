package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.Training;
import java.time.LocalDate;
import java.util.List;

public interface TrainingRepositoryCustom {
    List<Training> findTrainingsByTraineeUsernameAndCriteria(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String trainerName,
            String trainingType
    );
    List<Training> findTrainingsByTrainerUsernameAndCriteria(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String traineeName
    );
}
