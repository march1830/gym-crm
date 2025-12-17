package com.yourcompany.gym.cucumber.steps;

import com.yourcompany.gym.cucumber.TestContext;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CommonSteps {

    @Autowired private TestContext context;

    @Then("User receives a {int} {word} response")
    public void checkResponseStatus(int status, String ignoredText) {
        Assertions.assertEquals(
                HttpStatus.valueOf(status),
                context.getLastResponse().getStatusCode()
        );
    }
}