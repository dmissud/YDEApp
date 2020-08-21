# Created by dan at 09/08/2020
Feature: # Organization Management
  # Add / Remove / Update a Organization

  Scenario: Initialise a new organization
    Given The organization doesn't exist
    When Administrator want to create a new organization with idRefog "100000" and with name "DEP 01"
    And with the list of organisation in childs
      | Organization name | idRefog |
      | GROUPE 01         | 100010  |
      | GROUPE 02         | 100020  |
      | GROUPE 03         | 100030  |
      | GROUPE 04         | 100040  |
    Then a new organization tree is created
