Feature: Data Validation

  Background:
    Given User registers a new trainee with name "Valid" "User"
    And User registers a new trainer with name "Valid" "Trainer"
    And User logins as trainee "Valid" "User"

  Scenario: User cannot register with invalid name
    When User tries to register a trainee with name "Terminator2" "Connor"
    Then User receives a 400 BAD_REQUEST response

  Scenario: User cannot add training in the past
    When User tries to add a training "Back to Future" with date "2000-01-01"
    Then User receives a 400 BAD_REQUEST response