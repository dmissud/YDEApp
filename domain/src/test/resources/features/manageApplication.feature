# Created by Dan at 08/07/2020
Feature: Management of a Application
  # Add / Update Application

  Scenario: Create a new Application
    Given The application doesn't exist
    When Administrator with the following Application attributes
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00001         | Test App         | Long description for Test app | 10000000            |
    And With Responsable
      | uid    | firstName | lastName | IdRefogOrganization |
      | 123456 | John      | Doe      | 10000000            |
    And With the cycle life
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       | 28/08/2020       |
    And Administrator want to create a new application
    Then The create of a new application is a success

  Scenario: Update a existing application
    Given The following application attributes
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00002         | Test App         | Long description for Test app | 10000000            |
    And With Responsable
      | uid    | firstName | lastName | IdRefogOrganization |
      | 123456 | John      | Doe      | 10000000            |
    And With the cycle life
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       | 28/08/2020       |
    And The application exist
    When Administrator want to update an application with the following attributes
      | codeApplication | shortDescription | longDescription             | IdRefogOrganization |
      | AP00002         | Test Appupdate   | Long description app update | 10000000            |
    Then the update is success


  Scenario: Update a existing application with CycleLife
    Given The following application attributes
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00002         | Test App         | Long description for Test app | 10000000            |
    And With Responsable
      | uid    | firstName | lastName | IdRefogOrganization |
      | 123456 | John      | Doe      | 10000000            |
    And With the cycle life
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       | 28/09/2020       |
    And The application exist
    When Administrator want to update an application with the cycle life
      | state      | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Désactivée | 01/01/2020     | 01/08/2020       | 28/08/2020       |
    Then the update of cycleLife is success


  Scenario: Create a new Application with date incorrect
    Given The application doesn't exist
    When Administrator with the following Application attributes
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00001         | Test App         | Long description for Test app | 10000000            |
    And With Responsable
      | uid    | firstName | lastName | IdRefogOrganization |
      | 123456 | John      | Doe      | 10000000            |
    And With the cycle life
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       |                  |
    And Administrator want to create a new application
    Then The create of a new application is a success