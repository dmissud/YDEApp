# Created by dan at 09/08/2020
Feature: # Organization Management
  # Add / Remove / Update a Organization

  Scenario: Initialise a new organization
    Given The organization doesn't exist
    When Administrator want to create a new organization with the structure
