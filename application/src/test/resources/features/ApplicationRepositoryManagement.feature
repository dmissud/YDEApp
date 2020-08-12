# Created by Dan at 09/07/2020
Feature: Management of the repository of Application
  # Enter feature description here

  Scenario: Referencing a new application
    Given Application with code "AP00001" is not on the repository
    When The administrator enrich the repository with this application with this data
      | codeApplication | shortDescription | longDescription               | uid        |firstName |lastName  |
      | AP00001         | Test App         | Long description for Test app | 123456     |John      |Doe       |
    Then a new application is in the repository with code "AP00001"

