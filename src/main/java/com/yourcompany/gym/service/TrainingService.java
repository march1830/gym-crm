package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Training;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingService {

    /**
     * Creates a new training in the system.
     * @param traineeUsername The username of the trainee.
     * @param trainerUsername The username of the trainer.
     * @param trainingName The name of the training.
     * @param trainingDate The date of the training.
     * @param trainingDuration The duration of the training in minutes.
     * @return The created Training object.
     */
    Training addTraining(String traineeUsername, String trainerUsername, String trainingName, LocalDate trainingDate, int trainingDuration);

    /**
     * Finds a training by its unique identifier.
     * @param id The ID of the training to find.
     * @return an Optional containing the training if found, otherwise an empty Optional.
     */
    Optional<Training> findById(Long id);

    /**
     * Returns a list of all trainings.
     * @return a list of all trainings.
     */
    List<Training> findAll();

    /**
     * Gets a list of a trainee's trainings based on specified criteria.
     * @param username The username of the trainee.
     * @param fromDate The start date of the search period.
     * @param toDate The end date of the search period.
     * @param trainerName The first name of the trainer.
     * @param trainingType The type of the training.
     * @return A list of trainings that match the criteria.
     */
    List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);

    /**
     * Gets a list of a trainer's trainings based on specified criteria.
     * @param username The username of the trainer.
     * @param fromDate The start date of the search period.
     * @param toDate The end date of the search period.
     * @param traineeName The first name of the trainee.
     * @return A list of trainings that match the criteria.
     */
    List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeName);
}