package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDAO traineeDAO;

    @Autowired
    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Override
    public Trainee create(Trainee trainee) {
        return traineeDAO.save(trainee);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return traineeDAO.findById(id);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return traineeDAO.findByUsername(username);
    }

    @Override
    public List<Trainee> findAll() {
        return traineeDAO.findAll();
    }

    @Override
    public Trainee update(Trainee trainee) {
        return traineeDAO.update(trainee);
    }

    @Override
    public void deleteById(Long id) {
        traineeDAO.deleteById(id);
    }
}