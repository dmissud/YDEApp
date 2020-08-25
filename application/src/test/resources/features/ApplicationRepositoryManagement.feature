# Created by Dan at 09/07/2020
Feature: Management of the repository of Application
  # Enter feature description here

  Scenario: Referencing a new application with exiting Organization
    Given Application with code "AP00010" is not on the repository
    And organization "10000000" is in the directory
    When The administrator enrich the repository with this application with this data
      | codeApplication | shortDescription | longDescription               | uid    | firstName | lastName | idRefogMoe | state  | dateOfCreation  | dateOfLastUpdate | dateEndInReality |
      | AP00010         | Test App         | Long description for Test app | 123456 | John      | Doe      | 10000000   | Active | 01/01/2020      | 01/08/2020       | 28/08/2020       |

    Then a new application is in the repository with code "AP00010" rattached to the Organization with idRefog "10000000"

  Scenario: Updating a application with other Organization
    Given Application with code "AP00010" is in the repository with this data
      | codeApplication | shortDescription | longDescription               | uid    | firstName | lastName | idRefogMoe | state  | dateOfCreation  | dateOfLastUpdate | dateEndInReality |
      | AP00010         | Test App         | Long description for Test app | 123456 | John      | Doe      | 10000000   | Active | 01/01/2020      | 01/08/2020       | 28/08/2020       |

    And organization "10000001" is in the directory
    When The administrator update the repository with this application with this data
      | codeApplication | shortDescription | longDescription                        | uid    | firstName | lastName | idRefogMoe | state  | dateOfCreation  | dateOfLastUpdate | dateEndInReality |
      | AP00010         | Test App Update  | Long description for Test app  Updated | 123456 | John      | Doe      | 10000001   | Active | 01/01/2020      | 01/08/2020       | 28/08/2020       |

    Then The application with code "AP00010" is updated in the repository
    And The application with code "AP00010" rattached to the Organization with idRefog "10000001"




