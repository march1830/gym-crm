package com.yourcompany.gym.facade;

import com.yourcompany.gym.dto.*;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.repository.TrainingTypeRepository;
import com.yourcompany.gym.service.AuthenticationService;
import com.yourcompany.gym.service.TraineeService;
import com.yourcompany.gym.service.TrainerService;
import com.yourcompany.gym.service.TrainingService;
import com.yourcompany.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final UserService userService;
    private final TrainingService trainingService;
    private final AuthenticationService authenticationService;
    private final TrainingTypeRepository trainingTypeRepository;

    @Autowired
    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     UserService userService,
                     TrainingService trainingService,
                     AuthenticationService authenticationService,
                     TrainingTypeRepository trainingTypeRepository) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.userService = userService;
        this.trainingService = trainingService;
        this.authenticationService = authenticationService;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    // --- Registration Methods (No Authentication) ---
    public RegistrationResponse createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    public RegistrationResponse createTrainer(String firstName, String lastName, Long specializationId) {
        return trainerService.createTrainerProfile(firstName, lastName, specializationId);
    }

    // --- Authentication Method ---
    public boolean checkUserCredentials(String username, String password) {
        return authenticationService.checkCredentials(username, password);
    }

    // --- Trainee Methods ---
    public TraineeProfileResponse getTraineeProfile(String username, String password) {
        authenticate(username, password);
        Trainee trainee = traineeService.selectTraineeProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found: " + username));
        return TraineeProfileResponse.fromEntity(trainee);
    }

    public TraineeProfileResponse updateTrainee(UpdateTraineeRequest request, String password) {
        authenticate(request.username(), password);
        Trainee updatedTrainee = traineeService.updateTraineeProfile(
                request.username(),
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.address(),
                request.isActive()
        );
        return TraineeProfileResponse.fromEntity(updatedTrainee);
    }

    public void deleteTrainee(String username, String password) {
        authenticate(username, password);
        traineeService.deleteProfileByUsername(username);
    }

    public List<TrainerInfo> updateTraineeTrainers(String traineeUsername, String password, List<String> trainerUsernames) {
        authenticate(traineeUsername, password);
        Set<Trainer> trainers = traineeService.updateTrainersList(traineeUsername, trainerUsernames);
        return trainers.stream()
                .map(TrainerInfo::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TrainerInfo> getUnassignedTrainersForTrainee(String traineeUsername, String password) {
        authenticate(traineeUsername, password);
        List<Trainer> trainers = trainerService.getUnassignedTrainers(traineeUsername);
        return trainers.stream()
                .map(TrainerInfo::fromEntity)
                .collect(Collectors.toList());
    }

    // --- Trainer Methods ---
    public TrainerProfileResponse getTrainerProfile(String username, String password) {
        authenticate(username, password);
        Trainer trainer = trainerService.selectTrainerProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found: " + username));
        return TrainerProfileResponse.fromEntity(trainer);
    }

    public TrainerProfileResponse updateTrainer(UpdateTrainerRequest request, String password) {
        authenticate(request.username(), password);
        TrainingType specialization = trainingTypeRepository.findById(request.specializationId())
                .orElseThrow(() -> new RuntimeException("TrainingType not found with id: " + request.specializationId()));
        Trainer updatedTrainer = trainerService.updateTrainerProfile(
                request.username(),
                request.firstName(),
                request.lastName(),
                specialization,
                request.isActive()
        );
        return TrainerProfileResponse.fromEntity(updatedTrainer);
    }

    // --- Training Methods ---
    public void addTraining(AddTrainingRequest request) {
        // The facade is responsible for the authentication check.
        authenticate(request.traineeUsername(), request.password());

        // After the check, it calls the service.
        trainingService.addTraining(
                request.traineeUsername(),
                request.trainerUsername(),
                request.trainingName(),
                request.trainingDate(),
                request.trainingDuration()
        );
    }

    public List<TrainingResponseDTO> getTraineeTrainings(String username, String password, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        authenticate(username, password);
        List<Training> trainings = trainingService.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);
        return trainings.stream().map(TrainingResponseDTO::forTrainee).collect(Collectors.toList());
    }

    public List<TrainingResponseDTO> getTrainerTrainings(String username, String password, LocalDate fromDate, LocalDate toDate, String traineeName) {
        authenticate(username, password);
        List<Training> trainings = trainingService.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeName);
        return trainings.stream().map(TrainingResponseDTO::forTrainer).collect(Collectors.toList());
    }

    // --- General User Methods ---
    public void changeUserPassword(String username, String oldPassword, String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    public void setUserActiveStatus(String username, String password, boolean isActive) {
        authenticate(username, password);
        userService.setActiveStatus(username, isActive);
    }

    // --- Private Helper Method ---
    private void authenticate(String username, String password) {
        if (!authenticationService.checkCredentials(username, password)) {
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }
}





