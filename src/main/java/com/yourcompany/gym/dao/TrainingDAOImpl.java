package com.yourcompany.gym.dao;

import com.yourcompany.gym.model.Training;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrainingDAOImpl implements TrainingDAO {

    private final Storage storage;
    private final AtomicLong idCounter = new AtomicLong(0);


    public TrainingDAOImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Training save(Training training) {
        long newId = idCounter.incrementAndGet();
        training.setId(newId);
        storage.getTrainings().put(newId, training);
        System.out.println("LOG: Saved new training with ID: " + newId);
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(storage.getTrainings().get(id));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(storage.getTrainings().values());
    }

    @Override
    public void deleteById(Long id) {
        storage.getTrainings().remove(id);
    }

    @Override
    public Training update(Training training) {
        if (training.getId() == null ||!storage.getTrainings().containsKey(training.getId())) {
            System.out.println("ERROR: Attempt to update non-existent training.");
            return null;
        }
        storage.getTrainings().put(training.getId(), training);
        return training;
    }
}
