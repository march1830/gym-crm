package com.yourcompany.gym.dao;

import com.yourcompany.gym.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TraineeDAOImpl implements TraineeDAO {

    private final Storage storage;
    private final AtomicLong idCounter = new AtomicLong(0);

    public TraineeDAOImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Trainee save(Trainee trainee) {
        long newId = idCounter.incrementAndGet();
        trainee.setId(newId);
        storage.getTrainees().put(newId, trainee);
        System.out.println("LOG: Saved new trainee with ID: " + newId);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {

        return Optional.ofNullable(storage.getTrainees().get(id));
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {

        return storage.getTrainees().values().stream()
                .filter(trainee -> trainee.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(storage.getTrainees().values());
    }

    @Override
    public void deleteById(Long id) {
        storage.getTrainees().remove(id);
    }

    @Override
    public Trainee update(Trainee trainee) {

        if (trainee.getId() == null ||!storage.getTrainees().containsKey(trainee.getId())) {

            System.out.println("ERROR: Attempt to update non-existent trainee.");
            return null;
        }
        storage.getTrainees().put(trainee.getId(), trainee);
        return trainee;
    }
}
