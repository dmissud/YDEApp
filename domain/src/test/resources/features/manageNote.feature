# Created by Emmanuel at 10/08/2020

Feature: Note management

  Scenario: creating a new application note
    Given existing codeApplication "AP00002" in the repository

    When application manager wants to reference a new noteTitle "First Note" and a new noteContent "This app is..." and a new noteCreationDate "05/01/2020"

    Then note reference succeeded with a new noteTitle "First Note" and a new noteContent "This app is..." and a new noteCreationDate "05/01/2020"


  Scenario: deleting an existing note
    Given existing codeApplication "AP00002" and existing note "The Note", "The content", "02/02/2020" in the repository

    When application manager wants to delete a note entitled "The Note"

    Then this note "The Note" is deleted