package com.yourcompany.gym.service;

import com.yourcompany.gym.dto.TraineeDTO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TraineeService {

    /**
     * Creates a new trainee profile in the system.
     * Generates a unique username and a random password.
     * @param firstName The trainee's first name.
     * @param lastName The trainee's last name.
     * @param dateOfBirth The trainee's date of birth.
     * @param address The trainee's address (optional).
     * @return The saved Trainee object with an assigned ID.
     */
    TraineeDTO createTraineeProfile(String firstName,
                                    String lastName,
                                    LocalDate dateOfBirth,
                                    String address);

    /**
     * Updates the profile of an existing trainee.
     * @param username The username of the trainee to update.
     * @param firstName The trainee's new first name.
     * @param lastName The trainee's new last name.
     * @param dateOfBirth The new date of birth.
     * @param address The new address.
     * @param isActive The new active status.
     * @return The updated Trainee object.
     */
    Trainee updateTraineeProfile(String username,
                                 String firstName,
                                 String lastName,
                                 LocalDate dateOfBirth,
                                 String address,
                                 boolean isActive);

    /**
     * Finds a trainee by their unique identifier.
     * @param id The ID of the trainee.
     * @return an Optional containing the trainee if found, otherwise an empty Optional.
     */
    Optional<Trainee> findById(Long id);

    /**
     * Finds a trainee by their username.
     * @param username the username.
     * @return an Optional containing the trainee if found, otherwise an empty Optional.
     */
    Optional<Trainee> selectTraineeProfileByUsername(String username);

    /**
     * Deletes a trainee profile by their username.
     * @param username The username of the profile to delete.
     */
    void deleteProfileByUsername(String username);

    /**
     * Returns a list of all trainees.
     * @return a list of all trainees.
     */
    List<Trainee> findAll();

    /**
     * Deletes a trainee by their unique identifier.
     * @param id The ID of the trainee to delete.
     */
    void deleteById(Long id);

    /**
     * Updates the list of trainers for a specific trainee.
     * @param traineeUsername The username of the trainee.
     * @param trainerUsernames A list of trainer usernames.
     * @return The updated set of trainers for the given trainee.
     */
    Set<Trainer> updateTrainersList(String traineeUsername, List<String> trainerUsernames);

}