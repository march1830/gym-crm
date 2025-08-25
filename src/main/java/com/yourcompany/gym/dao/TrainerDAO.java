package com.yourcompany.gym.dao;

import com.yourcompany.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerDAO {

    void deleteById(Long id);

    Trainer save(Trainer trainer);

    Optional<Trainer> findById(Long id);

    Optional<Trainer> findByUsername(String username);

    List<Trainer> findAll();

    Trainer update(Trainer trainer);
}
