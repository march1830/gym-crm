package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TrainerDAO;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.utils.IdGenerator; // Убедитесь, что путь к IdGenerator правильный
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainerDAOImpl implements TrainerDAO {

    // 1. Собственное, приватное хранилище для тренеров
    private final Map<Long, Trainer> trainers = new HashMap<>();
    private final IdGenerator idGenerator;

    // 2. Внедрение зависимости от IdGenerator через конструктор
    @Autowired
    public TrainerDAOImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Trainer save(Trainer trainer) {
        Long newId = idGenerator.getNextId();
        trainer.setId(newId);
        trainers.put(newId, trainer);
        System.out.println("LOG: Saved new trainer with ID: " + newId);
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(trainers.get(id));
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return trainers.values().stream()
                .filter(trainer -> trainer.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(trainers.values());
    }

    @Override
    public Trainer update(Trainer trainer) {
        if (trainer.getId() == null ||!trainers.containsKey(trainer.getId())) {
            System.out.println("ERROR: Attempt to update non-existent trainer.");
            return null;
        }
        trainers.put(trainer.getId(), trainer);
        return trainer;
    }

    @Override
    public void deleteById(Long id) {
        trainers.remove(id);
    }
}