package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Training;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingService {

    /**
     * Создает новую тренировку в системе.
     * @param traineeUsername Имя пользователя стажера
     * @param trainerUsername Имя пользователя тренера
     * @param trainingName Название тренировки
     * @param trainingDate Дата тренировки
     * @param trainingDuration Длительность тренировки в минутах
     * @return Созданный объект Training
     */
    Training addTraining(String traineeUsername, String trainerUsername, String trainingName, LocalDate trainingDate, int trainingDuration);

    Optional<Training> findById(Long id);

    List<Training> findAll();

    List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);

    List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeName);
}