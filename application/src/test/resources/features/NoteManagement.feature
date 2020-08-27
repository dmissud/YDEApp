# By Emmanuel on 12/08/2020

Feature: Note management test

  Scenario: Referencing a new note
    Given Application referenced as "AP00002" in repository
    When User wants to reference a new note with following data
        |noteTitle   |noteContent                |noteCreationDate|
        |The note    |Once upon a time...        |30/02/2020      |
    Then new note is referenced to application code "AP00002" with title "The note"

  Scenario: Referencing a new note to unknown application
    Given Application referenced as "AP00002" unknown in repository
    When User wants to reference a new note to application "AP00002" with following data
      |noteTitle   |noteContent                |noteCreationDate|
      |The note    |Once upon a time...        |30/02/2020      |
    Then new exception is thrown for application "AP00002"

    Scenario: Updating an existing note
      Given An existing note referenced "The note", "Once upon a time...", "30/02/2020" to an application "AP00002"
      When User wants to update note "The note" with data
        |noteTitle      |noteContent                |noteCreationDate|
        |The note follow|The end                    |30/03/2020      |
      Then Note "The note follow" has been updated

    Scenario: getting a note by title
      Given An existing application "AP00002" with an existing note "The note", "The content", "02/02/2020"
      When User wants to get a note entitled "The note" referenced to application "AP00002"
      Then note "The note" is provided

    Scenario: getting all application notes
      Given An existing application "AP00002" with existing notes "Note 1", "Content 1", "Date 1" and "Note 2", "Content 2", "Date 2"
      When User wants to get a note-list
      Then All notes "Note 1" and "Note 2" are provided

    Scenario: deleting a note by title
      Given An existing application "AP00002" with the note "The note", "The content", "27/08/2020"
      When User wants to delete a note entitled "The Note"
      Then "The note" is deleted