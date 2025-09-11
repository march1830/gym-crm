package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {


    Optional<Trainer> findByUsername(String username);
    List<Trainer> findAllByIsActive(boolean isActive);
    List<Trainer> findAllByUserUsernameIn(List<String> usernames);
}
