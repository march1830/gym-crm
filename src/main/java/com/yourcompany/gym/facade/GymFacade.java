package com.yourcompany.gym.facade;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

    @Component
    public class GymFacade {

        private final TraineeService traineeService;

        @Autowired
        public GymFacade(TraineeService traineeService) {
            this.traineeService = traineeService;
        }

                public Trainee createTrainee(Trainee trainee) {
            return traineeService.createTraineeProfile(trainee);
        }
    }

