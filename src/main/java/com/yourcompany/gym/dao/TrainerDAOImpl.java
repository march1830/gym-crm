package com.yourcompany.gym.dao;

import com.yourcompany.gym.model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrainerDAOImpl implements TrainerDAO {

    private final Storage storage;
    private final AtomicLong idCounter = new AtomicLong(0);

    public TrainerDAOImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Trainer save(Trainer trainer) {
        long newId = idCounter.incrementAndGet();
        trainer.setId(newId);
        storage.getTrainers().put(newId, trainer);
        System.out.println("LOG: Saved new trainer with ID: " + newId);
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(storage.getTrainers().get(id));
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return storage.getTrainers().values().stream()
                .filter(trainer -> trainer.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(storage.getTrainers().values());
    }

    @Override
    public Trainer update(Trainer trainer) {
        if (trainer.getId() == null ||!storage.getTrainers().containsKey(trainer.getId())) {
            System.out.println("ERROR: Attempt to update non-existent trainer.");
            return null;
        }
        storage.getTrainers().put(trainer.getId(), trainer);
        return trainer;
    }
}