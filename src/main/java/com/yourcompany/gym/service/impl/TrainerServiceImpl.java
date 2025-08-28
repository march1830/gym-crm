package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dao.TrainerDAO;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public Trainer create(Trainer trainer) {
        return trainerDAO.save(trainer);
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return trainerDAO.findById(id);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return trainerDAO.findByUsername(username);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDAO.findAll();
    }

    @Override
    public Trainer update(Trainer trainer) {
        return trainerDAO.update(trainer);
    }

    @Override
    public void deleteById(Long id) {
        trainerDAO.deleteById(id);
    }
}