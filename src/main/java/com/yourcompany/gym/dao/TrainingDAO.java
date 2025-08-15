package com.yourcompany.gym.dao;

import com.yourcompany.gym.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingDAO {

    Training save(Training training);

    Optional<Training> findById(Long id);

    List<Training> findAll();

    void deleteById(Long id);

    Training update(Training training);
}
