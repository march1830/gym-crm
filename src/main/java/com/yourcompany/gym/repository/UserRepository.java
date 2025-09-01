package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA сам поймет, как реализовать этот метод по его названию.
    // Он будет проверять, существует ли в таблице 'users' запись с указанным username.
    boolean existsByUsername(String username);
}