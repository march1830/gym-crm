package com.yourcompany.gym.service;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.model.Trainee;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

      @Autowired
    private TraineeDAO traineeDAO;

    @Override
    public Trainee createTraineeProfile(Trainee trainee) {

        String baseUsername = trainee.getFirstName().toLowerCase() + "." + trainee.getLastName().toLowerCase();
        String finalUsername = baseUsername;
        int suffix = 1;
        while (traineeDAO.findByUsername(finalUsername).isPresent()) {
            finalUsername = baseUsername + suffix;
            suffix++;
        }
        trainee.setUsername(finalUsername);

        String password = RandomStringUtils.randomAlphanumeric(10);
        trainee.setPassword(password);

        trainee.setActive(true);

        return traineeDAO.save(trainee);
    }

    @Override
    public Optional<Trainee> selectTraineeProfileById(Long id) {
        return traineeDAO.findById(id);
    }

    @Override
    public Trainee updateTraineeProfile(Trainee trainee) {
        return traineeDAO.update(trainee);
    }

    @Override
    public void deleteTraineeProfileById(Long id) {
        traineeDAO.deleteById(id);
    }
}
