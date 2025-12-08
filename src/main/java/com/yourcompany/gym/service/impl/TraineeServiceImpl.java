package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dto.RegistrationResponse;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yourcompany.gym.utils.ValidationUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {


    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final int PASSWORD_LENGTH = 10;
    private final Counter traineeRegistrationCounter;


    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, TrainerRepository trainerRepository, MeterRegistry meterRegistry) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.traineeRegistrationCounter = Counter.builder("user.registrations.total")
                .tag("user_type", "trainee")
                .description("Total number of registered trainees")
                .register(meterRegistry);
    }

    @Override
    @Transactional
    public RegistrationResponse createTraineeProfile(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        log.info("Attempting to create a trainee profile for: {} {}", firstName, lastName);

                try {
            ValidationUtils.validateRequiredFields(firstName, lastName);
        } catch (IllegalArgumentException e) {
            log.error("Validation failed for creating trainee profile: {}", e.getMessage());
            throw e;
        }

        Trainee trainee = new Trainee();
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);
        trainee.setActive(true);

        String username = generateUsername(firstName, lastName);
        trainee.setUsername(username);
        log.debug("Generated username: {}", username);

        String password = generateRandomPassword(PASSWORD_LENGTH);
        log.info(">>>> Generated password for user '{}': {}", username, password);
        trainee.setPassword(passwordEncoder.encode(password));

        Trainee savedTrainee = traineeRepository.save(trainee);
        log.info("Successfully created trainee with ID: {} and username: {}", savedTrainee.getId(), savedTrainee.getUsername());

        this.traineeRegistrationCounter.increment();

              return new RegistrationResponse(savedTrainee.getUsername(), password);
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
    public Optional<Trainee> findById(Long id) {

        return traineeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trainee> selectTraineeProfileByUsername(String username) {
        Optional<Trainee> traineeOptional = traineeRepository.findByUsername(username);
        traineeOptional.ifPresent(trainee -> trainee.getTrainers().size());
        return traineeOptional;
    }

    @Override
    public List<Trainee> findAll() {

        return traineeRepository.findAll();
    }

    @Override
    @Transactional
    public Trainee updateTraineeProfile(String username, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive) {
        log.info("Attempting to update profile for trainee with username: {}", username);


        Trainee traineeToUpdate = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Trainee with username {} not found.", username);
                    return new RuntimeException("Trainee not found with username: " + username);
                });


        traineeToUpdate.setFirstName(firstName);
        traineeToUpdate.setLastName(lastName);
        traineeToUpdate.setDateOfBirth(dateOfBirth);
        traineeToUpdate.setAddress(address);
        traineeToUpdate.setActive(isActive);


        Trainee updatedTrainee = traineeRepository.save(traineeToUpdate);
        log.info("Successfully updated profile for trainee with ID: {}", updatedTrainee.getId());

        return updatedTrainee;
    }

    @Override
    @Transactional
    public void deleteProfileByUsername(String username) {
        log.info("Attempting to delete trainee profile with username: {}", username);


        Trainee traineeToDelete = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Trainee with username {} not found for deletion.", username);
                    return new RuntimeException("Trainee not found with username: " + username);
                });


        traineeRepository.delete(traineeToDelete);
        log.info("Successfully deleted trainee with username: {}", username);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        traineeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Set<Trainer> updateTrainersList(String traineeUsername, List<String> trainerUsernames) {
        log.info("Updating trainers list for trainee: {}", traineeUsername);


        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new RuntimeException("Trainee not found: " + traineeUsername));


        List<Trainer> trainersList = trainerRepository.findAllByUsernameIn(trainerUsernames);
        Set<Trainer> trainers = new HashSet<>(trainersList);


        trainee.setTrainers(trainers);


        traineeRepository.save(trainee);
        log.info("Successfully updated trainers list for trainee: {}", traineeUsername);

        return trainee.getTrainers();
    }


}