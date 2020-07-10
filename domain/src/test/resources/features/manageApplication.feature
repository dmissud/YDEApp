# Created by Dan at 08/07/2020
Feature: Management of a Application
  # Enter feature description here

  Scenario: Create a new Application
    Given The application doesn't exist in the repository
    When Administrator want to create a new application with the following attributes
      | codeApplication | shortDescription | longDescription               | nameOfResponsable |
      | AP00001         | Test App         | Long description for Test app | John Doe          |
    Then the create is success

  Scenario: Create a new Application with deffault value
    Given The application doesn't exist in the repository
    When Administrator want to create a new application with only code app AP00002
    Then the create is success with default field
