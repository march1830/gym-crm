package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TrainerDAO;
import com.yourcompany.gym.model.Trainer;
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
public class TrainerDAOImpl implements TrainerDAO {

    private final Map<Long, Trainer> trainerStorage;
    private final IdGenerator idGenerator;

    @Override
    public Trainer save(Trainer trainer) {
        Long newId = idGenerator.getNextId();
        trainer.setId(newId);
        trainerStorage.put(newId, trainer);
        log.info("Saved new trainer with ID: " + newId);
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return trainerStorage.values().stream()
                .filter(trainer -> trainer.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(trainerStorage.values());
    }

    @Override
    public Trainer update(Trainer trainer) {
        if (trainer.getId() == null ||!trainerStorage.containsKey(trainer.getId())) {
            log.warn("ERROR: Attempt to update non-existent trainer.");
            return null;
        }
        trainerStorage.put(trainer.getId(), trainer);
        return trainer;
    }

    @Override
    public void deleteById(Long id) {
        trainerStorage.remove(id);
    }
}