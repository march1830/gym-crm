package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dao.TrainerDAO;
import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    @Autowired
    public TrainerServiceImpl(TrainerDAO trainerDAO, TraineeDAO traineeDAO) {
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
    }

    @Override
    public Trainer create(Trainer trainer) {

        // TODO: В будущем здесь будет генерация случайного пароля
        // trainee.setPassword(generateRandomPassword());

        String username = generateUniqueUsername(trainer.getFirstName(), trainer.getLastName());
        trainer.setUsername(username);

        return trainerDAO.save(trainer);
    }

    private String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        int counter = 1;

        // Проверяем уникальность среди Trainee и Trainer
        while (trainerDAO.findByUsername(finalUsername).isPresent() || traineeDAO.findByUsername(finalUsername).isPresent()) {
            finalUsername = baseUsername + counter;
            counter++;
        }

        return finalUsername;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return trainerDAO.findById(id);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return trainerDAO.findByUsername(username);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDAO.findAll();
    }

    @Override
    public Trainer update(Trainer trainer) {
        return trainerDAO.update(trainer);
    }

    @Override
    public void deleteById(Long id) {
        trainerDAO.deleteById(id);
    }
}