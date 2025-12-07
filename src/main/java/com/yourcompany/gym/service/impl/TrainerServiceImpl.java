package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dto.RegistrationResponse;
import com.yourcompany.gym.dto.TrainerDTO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.TrainingTypeRepository;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.TrainerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yourcompany.gym.utils.ValidationUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.List;
import java.util.Optional;

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
    private final TrainingTypeRepository trainingTypeRepository;
    private final Counter trainerRegistrationCounter;

    private static final int PASSWORD_LENGTH = 10;


    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, TraineeRepository traineeRepository, TrainingTypeRepository trainingTypeRepository, MeterRegistry meterRegistry) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.trainerRegistrationCounter = Counter.builder("user.registrations.total")
                .tag("user_type", "trainee")
                .description("Total number of registered trainees")
                .register(meterRegistry);
    }


    @Override
    @Transactional
    public RegistrationResponse createTrainerProfile(String firstName, String lastName, Long specializationId) {
        log.info("Attempting to create a trainer profile for: {} {}", firstName, lastName);

        ValidationUtils.validateRequiredFields(firstName, lastName, specializationId);

        TrainingType specialization = trainingTypeRepository.findById(specializationId)
                .orElseThrow(() -> new RuntimeException("TrainingType not found with id: " + specializationId));

        Trainer trainer = new Trainer();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        String username = generateUsername(firstName, lastName);
        trainer.setUsername(username);

        String password = generateRandomPassword();
        log.info(">>>> Generated password for user '{}': {}", username, password);
        trainer.setPassword(passwordEncoder.encode(password));

        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Successfully created trainer with ID: {}", savedTrainer.getId());

        this.trainerRegistrationCounter.increment();

        return new RegistrationResponse(savedTrainer.getUsername(), password);
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

    private String generateRandomPassword() {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
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

    public Trainer updateTrainerProfile(String username, String firstName, String lastName, boolean isActive) {
        log.info("Attempting to update profile for trainer with username: {}", username);

        Trainer trainerToUpdate = trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Trainer with username {} not found.", username);
                    return new RuntimeException("Trainer not found with username: " + username);
                });

        trainerToUpdate.setFirstName(firstName);
        trainerToUpdate.setLastName(lastName);
        trainerToUpdate.setActive(isActive);



        Trainer updatedTrainer = trainerRepository.save(trainerToUpdate);
        log.info("Successfully updated profile for trainer with ID: {}", updatedTrainer.getId());

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