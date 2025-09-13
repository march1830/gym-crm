package com.yourcompany.gym.service;

import com.yourcompany.gym.dto.RegistrationResponse;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.dto.TrainerDTO;

import java.util.List;
import java.util.Optional;


public interface TrainerService {

    /**
     * Creates a new trainer profile in the system.
     * Generates a unique username and a random password.
     * @param firstName The trainer's first name.
     * @param lastName The trainer's last name.
     * @param specializationId The trainer's specialization (training type).
     * @return The saved Trainer object with an assigned ID.
     */
    RegistrationResponse createTrainerProfile(String firstName,
                                              String lastName,
                                              Long specializationId);

    /**
     * Updates the profile of an existing trainer.
     * @param username The username of the trainer to update.
     * @param firstName The trainer's new first name.
     * @param lastName The trainer's new last name.
     * @param specialization The trainer's new specialization.
     * @param isActive The new active status.
     * @return The updated Trainer object.
     */
    Trainer updateTrainerProfile(String username,
                                 String firstName,
                                 String lastName,
                                 TrainingType specialization,
                                 boolean isActive);

    /**
     * Finds a trainer by their unique identifier.
     * @param id The ID of the trainer.
     * @return an Optional containing the trainer if found, otherwise an empty Optional.
     */
    Optional<Trainer> findById(Long id);

    /**
     * Finds a trainer profile by their username.
     * @param username The username to search for.
     * @return an Optional containing the trainer's profile if found.
     */
    Optional<Trainer> selectTrainerProfileByUsername(String username);

    /**
     * Returns a list of all trainers.
     * @return a list of all trainers.
     */
    List<Trainer> findAll();

    /**
     * Deletes a trainer by their unique identifier.
     * @param id The ID of the trainer to delete.
     */
    void deleteById(Long id);

    /**
     * Gets a list of active trainers who are not assigned to the specified trainee.
     * @param traineeUsername The username of the trainee.
     * @return A list of unassigned trainers.
     */
    List<Trainer> getUnassignedTrainers(String traineeUsername);

}