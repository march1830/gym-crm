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

    // --- Методы создания остаются без аутентификации ---
    public RegistrationResponse createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    public RegistrationResponse createTrainer(String firstName, String lastName, Long specializationId) { // <-- Changed
        return trainerService.createTrainerProfile(firstName, lastName, specializationId); // <-- Changed
    }

    public boolean checkUserCredentials(String username, String password) {
        return authenticationService.checkCredentials(username, password);
    }

    public TraineeProfileResponse getTraineeProfile(String username, String password) {
        // First, authenticate the user
        authenticate(username, password);

        // Find the trainee entity using the existing service method
        Trainee trainee = traineeService.selectTraineeProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found: " + username)); // Replace with custom exception later

        // Convert the entity to the response DTO
        return TraineeProfileResponse.fromEntity(trainee);
    }
    // Inside public class GymFacade { ... }

    public TrainerProfileResponse getTrainerProfile(String username, String password) {
        // First, authenticate the user
        authenticate(username, password);

        // Find the trainer entity using the existing service method
        Trainer trainer = trainerService.selectTrainerProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found: " + username));

        // Convert the entity to the response DTO
        return TrainerProfileResponse.fromEntity(trainer);
    }

    public TrainerProfileResponse updateTrainer(UpdateTrainerRequest request, String password) {
        // First, authenticate the user
        authenticate(request.username(), password);

        // We need the TrainingType entity, so we find it first
        TrainingType specialization = trainingTypeRepository.findById(request.specializationId())
                .orElseThrow(() -> new RuntimeException("TrainingType not found with id: " + request.specializationId()));

        // Call the service with the data from the DTO
        Trainer updatedTrainer = trainerService.updateTrainerProfile(
                request.username(),
                request.firstName(),
                request.lastName(),
                specialization,
                request.isActive()
        );

        // Convert the updated entity to a DTO for the response
        return TrainerProfileResponse.fromEntity(updatedTrainer);
    }



    public Trainee selectTrainee(String username, String password) {
        authenticate(username, password);
        return traineeService.selectTraineeProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));
    }

    public TraineeProfileResponse updateTrainee(UpdateTraineeRequest request, String password) {
        // First, authenticate the user
        authenticate(request.username(), password);

        // Call the service with the data from the DTO
        Trainee updatedTrainee = traineeService.updateTraineeProfile(
                request.username(),
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.address(),
                request.isActive()
        );

        // Convert the updated entity to a DTO for the response
        return TraineeProfileResponse.fromEntity(updatedTrainee);
    }

    public void deleteTrainee(String username, String password) {
        authenticate(username, password);
        traineeService.deleteProfileByUsername(username);
    }

    public Set<Trainer> updateTraineeTrainers(String username, String password, List<String> trainerUsernames) {
        authenticate(username, password);
        return traineeService.updateTrainersList(username, trainerUsernames);
    }

    public Trainer selectTrainer(String username, String password) {
        authenticate(username, password);
        return trainerService.selectTrainerProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    public Trainer updateTrainer(String username, String password, String firstName, String lastName, TrainingType specialization, boolean isActive) {
        authenticate(username, password);
        return trainerService.updateTrainerProfile(username, firstName, lastName, specialization, isActive);
    }

    public void changeUserPassword(String username, String oldPassword, String newPassword) {

        userService.changePassword(username, oldPassword, newPassword);
    }

    public void setUserActiveStatus(String username, String password, boolean isActive) {
        authenticate(username, password);
        userService.setActiveStatus(username, isActive);
    }

    public Training addTraining(String traineeUsername, String trainerUsername, String password, String trainingName, LocalDate trainingDate, int trainingDuration) {

        authenticate(traineeUsername, password);
        return trainingService.addTraining(traineeUsername, trainerUsername, trainingName, trainingDate, trainingDuration);
    }

    public List<Training> getTraineeTrainings(String username, String password, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        authenticate(username, password);
        return trainingService.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);
    }

    public List<Training> getTrainerTrainings(String username, String password, LocalDate fromDate, LocalDate toDate, String traineeName) {
        authenticate(username, password);
        return trainingService.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeName);
    }

    public List<Trainer> getUnassignedTrainers(String traineeUsername, String password) {
        authenticate(traineeUsername, password);
        return trainerService.getUnassignedTrainers(traineeUsername);
    }


    private void authenticate(String username, String password) {
        if (!authenticationService.checkCredentials(username, password)) {
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }
}






