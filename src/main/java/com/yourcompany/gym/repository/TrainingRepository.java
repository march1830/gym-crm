package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    // Пока оставляю пустым
}