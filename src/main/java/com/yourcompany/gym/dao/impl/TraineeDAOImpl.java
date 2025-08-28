package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.utils.IdGenerator; // Убедитесь, что путь к IdGenerator правильный
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class TraineeDAOImpl implements TraineeDAO {

    // 1. Собственное хранилище вместо старого Storage
    private final Map<Long, Trainee> trainees = new HashMap<>();
    private final IdGenerator idGenerator;

    @Autowired
    public TraineeDAOImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Trainee save(Trainee trainee) {
        Long newId = idGenerator.getNextId();
        trainee.setId(newId);
        trainees.put(newId, trainee);
        log.info("LOG: Saved new trainee with ID: " + newId);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(trainees.get(id));
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return trainees.values().stream()
                .filter(trainee -> trainee.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(trainees.values());
    }

    @Override
    public Trainee update(Trainee trainee) {
        if (trainee.getId() == null ||!trainees.containsKey(trainee.getId())) {
            log.warn("ERROR: Attempt to update non-existent trainee.");
            return null;
        }
        trainees.put(trainee.getId(), trainee);
        return trainee;
    }

    @Override
    public void deleteById(Long id) {
        trainees.remove(id);
    }
}