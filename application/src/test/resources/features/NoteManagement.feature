# By Emmanuel on 12/08/2020

Feature: Note management test

  Scenario: Referencing a new note
    Given Application referenced as "AP00002" in repository
    When User wants to reference a new note with following datas
        |noteTitle   |noteContent                |noteCreationDate|
        |The note    |Once upon a time...        |30/02/2020      |
    Then new note is referenced to application code "AP0002" with title "The note"