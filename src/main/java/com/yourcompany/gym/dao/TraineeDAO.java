package com.yourcompany.gym.dao;

import com.yourcompany.gym.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeDAO {

    Trainee save(Trainee trainee);

    Optional<Trainee> findById(Long id);

    Optional<Trainee> findByUsername(String username);

    List<Trainee> findAll();

    void deleteById(Long id);

    Trainee update(Trainee trainee);
}