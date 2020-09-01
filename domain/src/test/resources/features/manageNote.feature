# Created by Emmanuel at 10/08/2020

Feature: Note management

  Scenario: creating a new application note
    Given The following application attributes
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00002         | Test App         | Long description for Test app | 10000000            |
    And With Responsable
      | uid    | firstName | lastName | IdRefogOrganization |
      | 123456 | John      | Doe      | 10000000            |
    And With the cycle life
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       | 28/08/2020       |
    And The application exist in the repository

    When application manager wants to reference a new noteTitle "First Note" and a new noteContent "This app is..." and a new noteCreationDate "05/01/2020"

    Then note reference succeeded with a new noteTitle "First Note" and a new noteContent "This app is..." and a new noteCreationDate "05/01/2020"


  Scenario: deleting an existing note
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

    And existing note in the repository
    | noteTitle  | noteContent  | noteCreationDate |
    | The note   | The Content  | 28/08/2020       |

    When application manager wants to delete a note entitled "The Note"

    Then this note "The Note" is deleted