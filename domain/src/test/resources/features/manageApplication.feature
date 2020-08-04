# Created by Dan at 08/07/2020
Feature: Management of a Application
  # Enter feature description here

  Scenario: Create a new Application
    Given The application doesn't exist in the repository
    When Administrator want to create a new application with the following attributes
      | codeApplication | shortDescription | longDescription               | uid        |firstName |lastName     |
      | AP00001         | Test App         | Long description for Test app |123456      |John      |Doe          |
    Then the create is success

  Scenario: Create a new Application with deffault value
    Given The application doesn't exist in the repository
    When Administrator want to create a new application with only code app AP00002
    Then the create is success with default field

  Scenario: Update a existing application
    Given The application exist in the repository
      | codeApplication | shortDescription | longDescription               | uid        |firstName |lastName     |
      | AP00001         | Test App         | Long description for Test app |123456      |John      |Doe          |
    When Administrator want to create a new application with the following attributes
      | codeApplication | shortDescription | longDescription               | uid        |firstName |lastName     |
      | AP00001         | Test Appupdate   | Long description app update   |654321      |Johnny    |Update       |
    Then the update is success