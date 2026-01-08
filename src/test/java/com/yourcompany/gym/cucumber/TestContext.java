package com.yourcompany.gym.cucumber;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;

@Data
@Component
@ScenarioScope
public class TestContext {

    private String savedTraineeUsername;
    private String savedTrainerUsername;
    private String savedPassword;
    private String jwtToken;
    private ResponseEntity<String> lastResponse;

}


