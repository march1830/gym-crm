package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    // Аналогично, этот метод будет искать тренера (Trainer) по его username.
    Optional<Trainer> findByUsername(String username);
}
