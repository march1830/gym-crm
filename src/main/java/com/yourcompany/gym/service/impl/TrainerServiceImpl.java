package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dto.TrainerDTO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.TrainerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j; // <-- Для логирования
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yourcompany.gym.utils.ValidationUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;// <-- Для управления транзакциями

import java.security.SecureRandom;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {


    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final int PASSWORD_LENGTH = 10;


    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, TraineeRepository traineeRepository) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public TrainerDTO createTrainerProfile(String firstName, String lastName, TrainingType specialization) {
        log.info("Attempting to create a trainer profile for: {} {}", firstName, lastName);


        // Remark #3: Validation logic is moved to a separate utility class.
        try {
            ValidationUtils.validateRequiredFields(firstName, lastName);
        } catch (IllegalArgumentException e) {
            log.error("Validation failed for creating trainee profile: {}", e.getMessage());
            throw e; // Re-throw the exception after logging
        }

        Trainer trainer = new Trainer();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setSpecialization(specialization);
        trainer.setActive(true);


        String username = generateUsername(firstName, lastName);
        trainer.setUsername(username);
        log.debug("Generated username: {}", username);


        String password = generateRandomPassword(PASSWORD_LENGTH);
        trainer.setPassword(passwordEncoder.encode(password));
        trainer.setPassword(password);


        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Successfully created trainer with ID: {} and username: {}", savedTrainer.getId(), savedTrainer.getUsername());


        return TrainerDTO.fromEntity(savedTrainer);
    }



    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        int suffix = 1;

        while (userRepository.existsByUsername(finalUsername)) {
            finalUsername = baseUsername + suffix++;
        }
        return finalUsername;
    }

    private String generateRandomPassword(int length) {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }



    @Override
    public Optional<Trainer> findById(Long id) {

        return trainerRepository.findById(id);
    }

    @Override
    public Optional<Trainer> selectTrainerProfileByUsername(String username) {
        log.info("Selecting trainer profile by username: {}", username);

        return trainerRepository.findByUsername(username);
    }

    @Override
    public List<Trainer> findAll() {

        return trainerRepository.findAll();
    }

    @Override
    @Transactional
    public Trainer updateTrainerProfile(String username, String firstName, String lastName, TrainingType specialization, boolean isActive) {
        log.info("Attempting to update profile for trainer with username: {}", username);


        Trainer trainerToUpdate = trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Trainer with username {} not found.", username);
                    return new RuntimeException("Trainer not found with username: " + username); // В будущем здесь будет кастомный Exception
                });


        trainerToUpdate.setFirstName(firstName);
        trainerToUpdate.setLastName(lastName);
        trainerToUpdate.setSpecialization(specialization);
        trainerToUpdate.setActive(isActive);


        Trainer updatedTrainer = trainerRepository.save(trainerToUpdate);
        log.info("Successfully updated profile for trainee with ID: {}", updatedTrainer.getId());

        return updatedTrainer;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        trainerRepository.deleteById(id);
    }

    @Override
    public List<Trainer> getUnassignedTrainers(String traineeUsername) {
        log.info("Finding unassigned trainers for trainee: {}", traineeUsername);


        List<Trainer> allActiveTrainers = trainerRepository.findAllByIsActive(true);


        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new RuntimeException("Trainee not found: " + traineeUsername));


        Set<Long> assignedTrainerIds = trainee.getTrainers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());


        return allActiveTrainers.stream()
                .filter(trainer -> !assignedTrainerIds.contains(trainer.getId()))
                .toList();
    }

}