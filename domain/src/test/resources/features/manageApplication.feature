# Created by Dan at 08/07/2020
Feature: Management of a Application
  # Add / Update Application

  Scenario: Create a new Application
    Given The application doesn't exist
    When Administrator want to create a new application with the following attributes
      | codeApplication | shortDescription | longDescription               | uid    | firstName | lastName | IdRefogOrganization |
      | AP00001         | Test App         | Long description for Test app | 123456 | John      | Doe      | 10000000            |

    Then the create is success

  Scenario: Create a new Application with deffault value
    Given The application doesn't exist
    When Administrator want to create a new application with only code app AP00002
    Then the create is success with default field

  Scenario: Update a existing application
    Given The application exist
      | codeApplication | shortDescription | longDescription               | uid    | firstName | lastName | IdRefogOrganization |
      | AP00002         | Test App         | Long description for Test app | 123456 | John      | Doe      | 10000000            |
    When Administrator want to update an application with the following attributes
      | codeApplication | shortDescription | longDescription             | uid    | firstName | lastName | IdRefogOrganization |
      | AP00002         | Test Appupdate   | Long description app update | 654321 | Johnny    | Update   | 10000000            |
    Then the update is success


  Scenario: Update a existing application with CycleLife
    Given The application exist
      | codeApplication | shortDescription | longDescription               | uid    | firstName | lastName | IdRefogOrganization |
      | AP00002         | Test App         | Long description for Test app | 123456 | John      | Doe      | 10000000            |
    When Administrator want to update an application with the cycle life
      | codeApplication | state  | dateOfCreation  | dateOfLastUpdate | dateEndInReality |
      | AP00002         | Active | 01/01/2020      | 01/08/2020       | 28/08/2020       |
    Then the update of cycleLife is success