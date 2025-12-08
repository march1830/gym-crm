package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dto.TrainerWorkloadRequest;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.TrainingRepository;
import com.yourcompany.gym.security.JwtService;
import com.yourcompany.gym.service.TrainingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final Counter trainingsAddedCounter;
    private final RestTemplate restTemplate;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final WorkloadNotificationServiceImpl notificationService;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TraineeRepository traineeRepository, TrainerRepository trainerRepository, MeterRegistry meterRegistry, RestTemplate restTemplate, JwtService jwtService, UserDetailsService userDetailsService, WorkloadNotificationServiceImpl notificationService) {
        this.trainingRepository = trainingRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingsAddedCounter = meterRegistry.counter("trainings.added.total",
                "description", "Total number of trainings added");
        this.restTemplate = restTemplate;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional

    public Training addTraining(String traineeUsername, String trainerUsername, String trainingName, LocalDate trainingDate, int trainingDuration) {
        log.info("Attempting to add training '{}' for trainee '{}' with trainer '{}'",
                trainingName, traineeUsername, trainerUsername);


        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new RuntimeException("Trainee not found with username: " + traineeUsername));


        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(() -> new RuntimeException("Trainer not found with username: " + trainerUsername));

        TrainingType trainingType = trainer.getSpecialization();

        Training newTraining = new Training();
        newTraining.setTrainee(trainee);
        newTraining.setTrainer(trainer);
        newTraining.setTrainingType(trainingType);
        newTraining.setTrainingName(trainingName);
        newTraining.setTrainingDate(trainingDate);
        newTraining.setTrainingDuration(trainingDuration);

        Training savedTraining = trainingRepository.save(newTraining);
        log.info("Successfully added training with ID: {}", savedTraining.getId());

        this.trainingsAddedCounter.increment();
        notificationService.sendWorkload(trainer, savedTraining);

        return savedTraining;
    }

    @Override
    public Optional<Training> findById(Long id) {
        log.info("Finding training by ID: {}", id);
        return trainingRepository.findById(id);
    }

    @Override
    public List<Training> findAll() {
        log.info("Finding all trainings.");
        return trainingRepository.findAll();
    }
    @Override
    public List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        log.info("Searching trainings for trainee {} with criteria.", username);
        return trainingRepository.findTrainingsByTraineeUsernameAndCriteria(username, fromDate, toDate, trainerName, trainingType);
    }
    @Override
    public List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeName) {
        log.info("Searching trainings for trainer {} with criteria.", username);
        return trainingRepository.findTrainingsByTrainerUsernameAndCriteria(username, fromDate, toDate, traineeName);
    }
}