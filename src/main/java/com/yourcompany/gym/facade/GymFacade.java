package com.yourcompany.gym.facade;

import com.yourcompany.gym.dto.*;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.repository.TrainingTypeRepository;
import com.yourcompany.gym.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final TrainingTypeRepository trainingTypeRepository;

    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     UserService userService,
                     TrainingService trainingService,
                     TrainingTypeRepository trainingTypeRepository) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.userService = userService;
        this.trainingService = trainingService;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    // --- Registration Methods (Public) ---
    public RegistrationResponse createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    public RegistrationResponse createTrainer(String firstName, String lastName, Long specializationId) {
        return trainerService.createTrainerProfile(firstName, lastName, specializationId);
    }

    // --- Trainee Methods (Protected by Spring Security) ---
    @Transactional(readOnly = true)
    public TraineeProfileResponse getTraineeProfile(String username) {
        Trainee trainee = traineeService.selectTraineeProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found: " + username));
        return TraineeProfileResponse.fromEntity(trainee);
    }

    @Transactional
    public TraineeProfileResponse updateTrainee(UpdateTraineeRequest request) {
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

    public void deleteTrainee(String username) {
        traineeService.deleteProfileByUsername(username);
    }

    @Transactional
    public List<TrainerInfo> updateTraineeTrainers(String traineeUsername, List<String> trainerUsernames) {
        Set<Trainer> trainers = traineeService.updateTrainersList(traineeUsername, trainerUsernames);
        return trainers.stream()
                .map(TrainerInfo::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TrainerInfo> getUnassignedTrainersForTrainee(String traineeUsername) {
        List<Trainer> trainers = trainerService.getUnassignedTrainers(traineeUsername);
        return trainers.stream()
                .map(TrainerInfo::fromEntity)
                .collect(Collectors.toList());
    }

    // --- Trainer Methods (Protected by Spring Security) ---
    @Transactional(readOnly = true)
    public TrainerProfileResponse getTrainerProfile(String username) {
        Trainer trainer = trainerService.selectTrainerProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found: " + username));
        return TrainerProfileResponse.fromEntity(trainer);
    }

    @Transactional
    public TrainerProfileResponse updateTrainer(UpdateTrainerRequest request) {
        // We no longer need to find the specialization here.
        Trainer updatedTrainer = trainerService.updateTrainerProfile(
                request.username(),
                request.firstName(),
                request.lastName(),
                request.isActive()
        );
        return TrainerProfileResponse.fromEntity(updatedTrainer);
    }


    // --- Training Methods (Protected by Spring Security) ---
    @Transactional
    public void addTraining(AddTrainingRequest request) {
        trainingService.addTraining(
                request.traineeUsername(),
                request.trainerUsername(),
                request.trainingName(),
                request.trainingDate(),
                request.trainingDuration()
        );
    }

    @Transactional(readOnly = true)
    public List<TrainingResponseDTO> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        List<Training> trainings = trainingService.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);
        return trainings.stream().map(TrainingResponseDTO::forTrainee).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TrainingResponseDTO> getTrainerTrainings(String username, LocalDate fromDate, LocalDate toDate, String traineeName) {
        List<Training> trainings = trainingService.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeName);
        return trainings.stream().map(TrainingResponseDTO::forTrainer).collect(Collectors.toList());
    }

    // --- General User Methods (Protected by Spring Security) ---
    public void changeUserPassword(String username, String newPassword) {
        // The service layer will handle the logic, no need for old password here
        userService.changePassword(username, newPassword);
    }

    public void setUserActiveStatus(String username, boolean isActive) {
        userService.setActiveStatus(username, isActive);
    }
    public List<TrainingType> getAllTrainingTypes() {
        return trainingTypeRepository.findAll();
    }
}





