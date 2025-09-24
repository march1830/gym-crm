package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.TrainingTypeDTO;
import com.yourcompany.gym.facade.GymFacade;
import com.yourcompany.gym.model.TrainingType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/training-types")
public class TrainingTypeController {

    private final GymFacade gymFacade;

    public TrainingTypeController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @GetMapping
    public ResponseEntity<List<TrainingTypeDTO>> getTrainingTypes() {

        List<TrainingType> trainingTypes = gymFacade.getAllTrainingTypes();


        List<TrainingTypeDTO> dtos = trainingTypes.stream()
                .map(TrainingTypeDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}