package org.yde.ydeapp.domain.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.Note;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateNoteSteps {

    public static final String CODE_APPLICATION = "AP00002";
    private Note newNote = null;
    private Application application;


    @Given("existing codeApplication {string} in the repository")
    public void existing_code_application_in_the_repository(String codeApplication) {

        application = new Application.Builder(codeApplication).build();
    }

    @When("application manager wants to reference a new noteTitle {string} and a new noteContent {string} and a new noteCreationDate {string}")
    public void application_manager_wants_to_reference_a_new_note_title_and_a_new_note_content_and_a_new_note_creation_date(String noteTitle, String noteContent, String noteCreationDate) {

        newNote = new Note(noteTitle, noteContent, noteCreationDate);
        application.addNote(newNote);
    }

    @Then("note reference succeeded with a new noteTitle {string} and a new noteContent {string} and a new noteCreationDate {string}")
    public void note_reference_succeeded_with_a_new_note_title_and_a_new_note_content_and_a_new_note_creation_date(String noteTitle, String noteContent, String noteCreationDate) {
        assertThat(application.retrieveNotes()).isNotNull();
        assertThat(application.retrieveNotes().get(noteTitle)).isNotNull();
        assertThat(application.retrieveNotes().get(noteTitle).getNoteTitle()).isEqualTo(noteTitle);
        assertThat(application.retrieveNotes().get(noteTitle).getNoteContent()).isEqualTo(noteContent);
        assertThat(application.retrieveNotes().get(noteTitle).getNoteCreationDate()).isEqualTo(noteCreationDate);
    }

    @Given("existing codeApplication {string} and existing note {string}, {string}, {string} in the repository")
    public void existing_code_application_and_existing_note_in_the_repository(String codeApplication, String noteTitle, String noteContent, String noteCreationDate) {
        application = new Application.Builder(codeApplication).build();
        newNote = new Note(noteTitle, noteContent, noteCreationDate);
        application.addNote(newNote);

    }

    @When("application manager wants to delete a note entitled {string}")
    public void application_manager_wants_to_delete_a_note_entitled(String noteTitle) {
        application.deleteNote(noteTitle);
    }

    @Then("this note {string} is deleted")
    public void this_note_is_deleted(String noteTitle) {
        assertThat(application.retrieveNoteByTitle(noteTitle)).isNull();

    }
}
