package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TrainingDAO;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrainingDAOImpl implements TrainingDAO {

    private final Map<Long, Training> trainingStorage;
    private final IdGenerator idGenerator;

    @Override
    public Training save(Training training) {
        // У Training ID не генерируется автоматически, а присваивается в сервисе.
        // Но если мы решим создавать его здесь, логика будет такой:
        if (training.getId() == null) {
            Long newId = idGenerator.getNextId();
            training.setId(newId);
        }
        trainingStorage.put(training.getId(), training);
        log.info("LOG: Saved new training with ID: " + training.getId());
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(trainingStorage.get(id));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(trainingStorage.values());
    }

    @Override
    public Training update(Training training) {
        if (training.getId() == null ||!trainingStorage.containsKey(training.getId())) {
            log.warn("ERROR: Attempt to update non-existent training.");
            return null;
        }
        trainingStorage.put(training.getId(), training);
        return training;
    }

    @Override
    public void deleteById(Long id) {
        trainingStorage.remove(id);
    }
}