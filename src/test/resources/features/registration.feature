Feature: User Registration

  Scenario: User registers as Trainee successfully
    Given User registers a new trainee with name "Cucumber" "Trainee"
    Then User receives a 201 Created response

  Scenario: User registers as Trainer successfully
    Given User registers a new trainer with name "Java" "Trainer"
    Then User receives a 201 Created response