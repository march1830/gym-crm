Feature: User Authentication

  Scenario: User logins successfully with valid credentials

    Given User registers a new trainee with name "Auth" "User"
    When User logins as trainee "Auth" "User"
    Then User receives a valid JWT token

  Scenario: User fails to login with wrong password
    Given User registers a new trainee with name "Hacker" "Man"
    When User tries to login with wrong password
    Then User receives a 401 Unauthorized response