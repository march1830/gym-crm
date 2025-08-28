package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.model.Trainee;
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
public class TraineeDAOImpl implements TraineeDAO {

    private final Map<Long, Trainee> traineeStorage;
    private final IdGenerator idGenerator;

    @Override
    public Trainee save(Trainee trainee) {
        Long newId = idGenerator.getNextId();
        trainee.setId(newId);
        traineeStorage.put(newId, trainee);
        log.info("LOG: Saved new trainee with ID: " + newId);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(traineeStorage.get(id));
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return traineeStorage.values().stream()
                .filter(trainee -> trainee.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(traineeStorage.values());
    }

    @Override
    public Trainee update(Trainee trainee) {
        if (trainee.getId() == null ||!traineeStorage.containsKey(trainee.getId())) {
            log.warn("ERROR: Attempt to update non-existent trainee.");
            return null;
        }
        traineeStorage.put(trainee.getId(), trainee);
        return trainee;
    }

    @Override
    public void deleteById(Long id) {
        traineeStorage.remove(id);
    }
}