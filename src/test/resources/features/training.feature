Feature: Training Operations

  Background:
    Given User registers a new trainee with name "Gym" "Fan"
    And User registers a new trainer with name "Super" "Coach"
    And User logins as trainee "Gym" "Fan"

  Scenario: User adds a training successfully
    When User adds a training "Morning Strength" with duration 60 for trainer "Super" "Coach"
    Then User receives a 202 Accepted response
    And The workload message should be sent to "workload.topic" queue with action "ADD"