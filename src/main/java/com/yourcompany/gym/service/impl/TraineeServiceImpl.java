package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.dao.TrainerDAO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;

    @Autowired
    public TraineeServiceImpl(TraineeDAO traineeDAO, TrainerDAO trainerDAO) {
        this.traineeDAO = traineeDAO;
        this.trainerDAO = trainerDAO;
    }

    @Override
    public Trainee create(Trainee trainee) {

        // TODO: В будущем здесь будет генерация случайного пароля
        // trainee.setPassword(generateRandomPassword());

        String username = generateUniqueUsername(trainee.getFirstName(), trainee.getLastName());
        trainee.setUsername(username);

        return traineeDAO.save(trainee);
    }

    private String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        int counter = 1;

        // Проверяем уникальность среди Trainee и Trainer
        while (traineeDAO.findByUsername(finalUsername).isPresent() || trainerDAO.findByUsername(finalUsername).isPresent()) {
            finalUsername = baseUsername + counter;
            counter++;
        }

        return finalUsername;
    }


    @Override
    public Optional<Trainee> findById(Long id) {
        return traineeDAO.findById(id);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return traineeDAO.findByUsername(username);
    }

    @Override
    public List<Trainee> findAll() {
        return traineeDAO.findAll();
    }

    @Override
    public Trainee update(Trainee trainee) {
        return traineeDAO.update(trainee);
    }

    @Override
    public void deleteById(Long id) {
        traineeDAO.deleteById(id);
    }
}