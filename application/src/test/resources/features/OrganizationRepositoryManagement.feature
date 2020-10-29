# Created by danie at 11/08/2020
Feature: Organization management
  # Enter feature description here

  Scenario: referencing a new organization with no childs
    Given All The organization doesn't exist
    When Administrator want to create a new organization Tree based on organization with idRefog "10000000" and with name "DEP 01"
    Then a organization tree exist with base "DEP 01" with a total of "0" Childs and "1" Organizations

  Scenario: referencing a new organization with new childs
    Given All The organization doesn't exist
    When Administrator want to create a new organization Tree based on organization with idRefog "10000000" and  with name "DEP 01" and the following children
      | Organization name | idRefog |
      | GROUPE 01         | 10001000  |
      | GROUPE 02         | 10002000  |
    Then a organization tree exist with base "DEP 01" with a total of "2" Childs and "3" Organizations

  Scenario: updating the name of a organization
    Given The organization with idRefog "10000000" and with name "Name Old" exist
    When Administrator want change the name of the  organization with idRefog "10000000"  with "New name"
    Then the name of the organization "10000000" is "New name"

  Scenario: updating a organization with new childs
    Given The Organization with idRefog "10000000" and  with name "DEP 01" exist and have the following children
      | Organization name | idRefog |
      | GROUPE 01         | 10001000  |
      | GROUPE 02         | 10002000  |
    When Administrator want to update the organization Tree based on organization with idRefog "10000000" and  with name "DEP 01" and the following children
      | Organization name | idRefog |
      | GROUPE 03         | 10003000  |
      | GROUPE 04         | 10004000  |
    Then a organization tree exist with base "DEP 01" with a total of "4" Childs and "5" Organizations

  Scenario: retrieve a organization with child
    Given The organization with idRefog "10000000" and with name "DEP 01" exist in the repository
    When Administrator want to consult a organization Tree based on organization with idRefog "10000000"
    Then a organization tree exist with base "DEP 01" with a total of "0" Childs and "1" Organizations
