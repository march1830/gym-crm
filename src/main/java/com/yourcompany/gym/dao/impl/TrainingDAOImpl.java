package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TrainingDAO;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.utils.IdGenerator; // Убедитесь, что путь к IdGenerator правильный
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingDAOImpl implements TrainingDAO {

    // 1. Собственное хранилище вместо старого Storage
    private final Map<Long, Training> trainings = new HashMap<>();
    private final IdGenerator idGenerator;

    @Autowired
    public TrainingDAOImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Training save(Training training) {
        // У Training ID не генерируется автоматически, а присваивается в сервисе.
        // Но если мы решим создавать его здесь, логика будет такой:
        if (training.getId() == null) {
            Long newId = idGenerator.getNextId();
            training.setId(newId);
        }
        trainings.put(training.getId(), training);
        System.out.println("LOG: Saved new training with ID: " + training.getId());
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(trainings.get(id));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(trainings.values());
    }

    @Override
    public Training update(Training training) {
        if (training.getId() == null ||!trainings.containsKey(training.getId())) {
            System.out.println("ERROR: Attempt to update non-existent training.");
            return null;
        }
        trainings.put(training.getId(), training);
        return training;
    }

    @Override
    public void deleteById(Long id) {
        trainings.remove(id);
    }
}