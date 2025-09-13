package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
}
