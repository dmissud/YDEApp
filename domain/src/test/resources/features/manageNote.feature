# Created by Emmanuel at 10/08/2020

Feature: Note management

  Background:
    Given The following application attributes
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00002         | Test App         | Long description for Test app | 10000000            |
    And With Responsable
      | uid    | firstName | lastName | IdRefogOrganization |
      | 123456 | John      | Doe      | 10000000            |
    And With the cycle life
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       | 28/08/2020       |
    And With it solution
      | typeOfSolution  | nameOfFirmware | LabelOfSourcing |
      | Open            | Toto           | TMA             |
    And The application exist in the repository

  Scenario: creating a new application note
    Given a user consult the application
    When The user wants to reference a new note
      | noteTitle  | noteContent    | noteCreationDate |
      | First Note | This app is... | 05/01/2020       |
    Then note reference succeeded with
      | noteTitle  | noteContent    | noteCreationDate |
      | First Note | This app is... | 05/01/2020       |


  Scenario: deleting an existing note
    Given a user consult the application
    And existing note in the repository
      | noteTitle | noteContent | noteCreationDate |
      | The note  | The Content | 28/08/2020       |
    When the user wants to delete a note entitled "The Note"
    Then this note "The Note" is deleted