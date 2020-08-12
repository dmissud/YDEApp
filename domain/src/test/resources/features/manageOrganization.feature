# Created by dan at 09/08/2020
Feature: # Organization Management
  # Add / Remove / Update a Organization

  Scenario: Initialise a new organization
    Given The organization doesn't exist
    When Administrator want to create a new organization with name "DEP 01"
    And with the list of organisation in childs
      | Organization name |
      | GROUPE 01         |
      | GROUPE 02         |
      | GROUPE 03         |
      | GROUPE 04         |
    Then a new organization tree is created
