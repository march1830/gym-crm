package com.yourcompany.gym.facade;


import com.yourcompany.gym.dto.TrainerDTO;
import com.yourcompany.gym.dto.TraineeDTO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.service.AuthenticationService;
import com.yourcompany.gym.service.TraineeService;
import com.yourcompany.gym.service.TrainerService;
import com.yourcompany.gym.service.TrainingService;
import com.yourcompany.gym.service.UserService;
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

    public GymFacade(TraineeService traineeService, TrainerService trainerService, UserService userService, TrainingService trainingService, AuthenticationService authenticationService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.userService = userService;
        this.trainingService = trainingService;
        this.authenticationService = authenticationService;
    }

    // --- Методы создания остаются без аутентификации ---
    public TraineeDTO createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    public TrainerDTO createTrainer(String firstName, String lastName, TrainingType specialization) {
        return trainerService.createTrainerProfile(firstName, lastName, specialization);
    }



    public Trainee selectTrainee(String username, String password) {
        authenticate(username, password);
        return traineeService.selectTraineeProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));
    }

    public Trainee updateTrainee(String username, String password, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive) {
        authenticate(username, password);
        return traineeService.updateTraineeProfile(username, firstName, lastName, dateOfBirth, address, isActive);
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






