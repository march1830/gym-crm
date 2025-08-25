package com.yourcompany.gym.dao.impl;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import org.springframework.stereotype.Repository;
import com.yourcompany.gym.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TraineeDAOImpl implements TraineeDAO {

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
        System.out.println("LOG: Saved new trainee with ID: " + newId);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) { // 4. Правильный тип возвращаемого значения
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
    public void deleteById(Long id) {
        trainees.remove(id);
    }

    @Override
    public Trainee update(Trainee trainee) {
        if (trainee.getId() == null ||!trainees.containsKey(trainee.getId())) {
            System.out.println("ERROR: Attempt to update non-existent trainer.");
            return null;
        }
        trainees.put(trainee.getId(), trainee);
        return trainee;
    }
}
