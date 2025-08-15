package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import java.util.Optional;

public interface TraineeService {
    Trainee createTraineeProfile(Trainee trainee);
    Optional<Trainee> selectTraineeProfileById(Long id);
    Trainee updateTraineeProfile(Trainee trainee);
    void deleteTraineeProfileById(Long id);
}
