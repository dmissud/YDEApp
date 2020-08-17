# Created by danie at 11/08/2020
Feature: Organization management
  # Enter feature description here

  Scenario: referencing a new organization with no childs
    Given All The organization doesn't exist
    When Administrator want to create a new organization Tree based on organization with name "DEP 01"
    Then a new organization tree is created with base "DEP 01" with a total of "0" Childs and "1" Organizations

  Scenario: referencing a new organization with new childs
    Given All The organization doesn't exist
    When Administrator want to create a new organization Tree based on organization with idRefog "100000" and  with name "DEP 01" and the following children
      | Organization name | idRefog | Parent name |
      | GROUPE 01         | 100010  | DEP 01      |
      | GROUPE 02         | 100020  | DEP 01      |
    Then a new organization tree is created with base "DEP 01" with a total of "2" Childs and "3" Organizations
