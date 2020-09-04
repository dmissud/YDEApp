# Created by Dan at 09/07/2020
Feature: Management of the repository of Application
  # Enter feature description here

  Scenario: Referencing a new application with exiting Organization
    Given Application with code "AP00010" is not on the repository
    And organization "10000000" is in the directory
    When The administrator enrich the repository with this application with this data
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00001         | Test App         | Long description for Test app | 10000000            |
    And With Responsable create
      | uid    | firstName | lastName | IdRefogOrganization |
      | 123456 | John      | Doe      | 10000000            |
    And With the cycle life create
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       | 28/08/2020       |
    Then The application with code "AP00001" is created in the repository
    And The application with code "AP00001" rattached to the Organization with idRefog "10000000"

  Scenario: Updating a application with other Organization
    Given Application is in the repository with this data
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00001         | Test App         | Long description for Test app | 10000001            |
    And control organization "10000001" is in the directory
    And With Responsable
      | uid    | firstName | lastName |IdRefogOrganization |
      | 123456 | John      | Doe      |10000001            |
    And With the cycle life
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/01/2020     | 01/08/2020       | 28/08/2020       |
    When The administrator update the repository with this application with this data
      | codeApplication | shortDescription | longDescription               | IdRefogOrganization |
      | AP00001         | Test App update  | Long description for update   | 10000001            |
    And With Responsable update
      | uid    | firstName | lastName |
      | 123457 | Johnup    | Doeup    |
    And With the cycle life update
      | state  | dateOfCreation | dateOfLastUpdate | dateEndInReality |
      | Active | 01/02/2020     | 01/07/2020       | 28/07/2020       |
    Then The application with code "AP00001" is updated in the repository
    And The application with code "AP00001" rattached to the Organization with idRefog "10000001"




