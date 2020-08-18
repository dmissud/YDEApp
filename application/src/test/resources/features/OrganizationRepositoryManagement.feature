# Created by danie at 11/08/2020
Feature: Organization management
  # Enter feature description here

  Scenario: referencing a new organization with no childs
    Given All The organization doesn't exist
    When Administrator want to create a new organization Tree based on organization with idRefog "10000000" and with name "DEP 01"
    Then a new organization tree is created with base "DEP 01" with a total of "0" Childs and "1" Organizations

  Scenario: referencing a new organization with new childs
    Given All The organization doesn't exist
    When Administrator want to create a new organization Tree based on organization with idRefog "10000000" and  with name "DEP 01" and the following children
      | Organization name | idRefog |
      | GROUPE 01         | 10001000  |
      | GROUPE 02         | 10002000  |
    Then a new organization tree is created with base "DEP 01" with a total of "2" Childs and "3" Organizations

  Scenario: updating the name of a organization
    Given The organization with idRefog "10000000" and with name "Name Old" exist
    When Administrator want change the name of the  organization with idRefog "10000000"  with "New name"
    Then tne name of the organization "10000000" is "New name"

