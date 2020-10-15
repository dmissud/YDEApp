# new feature
# Tags: optional

Feature: user management

  Scenario: creating a new user
    Given a admin creates a new user
    When admin reference new user with firstName "Cristiano" and with lastName "Ronaldo" and with uid "150233" and with password "frhfurv" and with roles "4"
    Then user reference succeeded
