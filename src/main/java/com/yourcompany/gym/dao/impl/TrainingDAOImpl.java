package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TrainingDAO;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingDAOImpl implements TrainingDAO {

    // ИСПРАВЛЕНО: имя переменной trainings
    private final Map<Long, Training> trainings = new HashMap<>();
    private final IdGenerator idGenerator;

    @Autowired
    public TrainingDAOImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Training save(Training training) {
        Long newId = idGenerator.getNextId();
        training.setId(newId);
        trainings.put(newId, training); // ИСПРАВЛЕНО
        System.out.println("LOG: Saved new training with ID: " + newId);
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(trainings.get(id)); // ИСПРАВЛЕНО
    }

    // ИСПРАВЛЕНО: Метод findByUsername удален, так как у Training нет username

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(trainings.values()); // ИСПРАВЛЕНО
    }

    @Override
    public void deleteById(Long id) {
        trainings.remove(id); // ИСПРАВЛЕНО
    }

    @Override
    public Training update(Training training) {
        if (training.getId() == null ||!trainings.containsKey(training.getId())) { // ИСПРАВЛЕНО
            System.out.println("ERROR: Attempt to update non-existent training.");
            return null;
        }
        trainings.put(training.getId(), training); // ИСПРАВЛЕНО
        return training;
    }
}

