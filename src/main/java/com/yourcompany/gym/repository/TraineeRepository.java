package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    // Этот метод будет искать стажера (Trainee) по полю username, которое находится в его родительской сущности (User).
    Optional<Trainee> findByUsername(String username);
}