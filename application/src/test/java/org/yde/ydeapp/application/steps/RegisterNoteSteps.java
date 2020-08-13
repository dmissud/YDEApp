package org.yde.ydeapp.application.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.yde.ydeapp.application.in.ReferenceNoteUseCase;
import org.yde.ydeapp.application.service.NoteManagementService;
import org.yde.ydeapp.domain.Application;

import org.yde.ydeapp.domain.Note;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class RegisterNoteSteps {

    @InjectMocks
    NoteManagementService noteManagementService;

    @Mock
    private RepositoryOfApplication repositoryOfApplication;

    private Application application;
    private EntityNotFound applicationNotFound;
    private Note note = null;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataTableType
    public ReferenceNoteUseCase.ReferenceNoteCmd noteDataTableEntry(Map<String, String> entry) {
        return new ReferenceNoteUseCase.ReferenceNoteCmd(
                entry.get("noteTitle"),
                entry.get("noteContent"),
                entry.get("noteCreationData"));

    }

    @Given("Application referenced as {string} in repository")
    public void application_referenced_as_in_repository(String codeApplication) {
        application = new Application.Builder(codeApplication).build();
        Mockito
                .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
                .thenReturn(application);

    }


    @When("User wants to reference a new note with following datas")
    public void user_wants_to_reference_a_new_note_with_following_datas(List<ReferenceNoteUseCase.ReferenceNoteCmd> notes) {
        ReferenceNoteUseCase.ReferenceNoteCmd noteTable;
        if (notes.size() == 1) {
            noteTable = notes.get(0);

        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }
        noteManagementService.referenceNote(application.getCodeApplication(), noteTable);

    }
    @Then("new note is referenced to application code {string} with title {string}")
    public void new_note_is_referenced_to_application_code_with_title(String codeApplication, String noteTitle) {
        assertThat(application.getCodeApplication()).isEqualTo(codeApplication);
        assertThat(application.retrieveNoteByTitle(noteTitle)).isNotNull();

        Mockito
                .verify(repositoryOfApplication, Mockito.times(1))
                .updateApplication(application);

    }

    @Given("Application referenced as {string} unknown in repository")
    public void application_referenced_as_unknown_in_repository(String codeApplication) {
        Mockito
                .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
                .thenThrow(new EntityNotFound(String.format("Unknown application referenced with codeApplication : %s", codeApplication)));
        application = null;
        applicationNotFound = null;
    }

    @When("User wants to reference a new note to application {string} with following datas")
    public void user_wants_to_reference_a_new_note_to_application_with_following_datas(String codeApplication, List<ReferenceNoteUseCase.ReferenceNoteCmd> notes) {
        try {
            noteManagementService.referenceNote(codeApplication, notes.get(0));
        } catch (EntityNotFound entityNotFound) {
            applicationNotFound = entityNotFound;
        }

    }

    @Then("new exception is thrown for application {string}")
    public void new_exception_is_thrown_for_application(String codeApplication) {
        assertThat(applicationNotFound).isNotNull();
        assertThat(applicationNotFound.getMessage()).isEqualTo(String.format("Unknown application referenced with codeApplication : %s", codeApplication));

    }

    @Given("An existing note referenced {string}, {string}, {string} to an application {string}")
    public void an_existing_note_referenced_to_an_application(String noteTitle, String noteContent, String noteCreationDate, String codeApplication) {

        application = new Application.Builder(codeApplication).build();
        note = new Note(noteTitle, noteContent, noteCreationDate);
        application.addNote(note);
        Mockito
                .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
                .thenReturn(application);

    }

    @When("User wants to update note {string} with datas")
    public void user_wants_to_update_note_with_datas(String noteTitle, List<ReferenceNoteUseCase.ReferenceNoteCmd> notes) {

        ReferenceNoteUseCase.ReferenceNoteCmd noteTable;
        if (notes.size() == 1) {
            noteTable = notes.get(0);

        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }
        noteManagementService.updateNote(application.getCodeApplication(), noteTitle, noteTable);
    }

    @Then("Note {string} has been updated")
    public void note_has_been_updated(String noteTitle) {

        Mockito
                .verify(repositoryOfApplication, Mockito.times(1))
                .updateApplication(application);

        //assertThat(application.retrieveNoteByTitle(noteTitle).getNoteTitle()).isEqualTo(noteTitle);

    }

    @Given("An existing application {string} with an existing note {string}, {string}, {string}")
    public void an_existing_application(String codeApplication, String noteTitle, String noteContent, String noteCreationDate) {

        application = new Application.Builder(codeApplication).build();
        note = new Note(noteTitle, noteContent, noteCreationDate);
        application.addNote(note);
        Mockito
                .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
                .thenReturn(application);
    }


    @When("User wants to get a note entitled {string} referenced to application {string}")
    public void user_wants_to_get_a_note_entitled_referenced_to_application(String noteTitle, String codeApplication) {

      note = noteManagementService.getApplicationNoteByTitle(codeApplication, noteTitle);
    }

    @Then("note {string} is provided")
    public void note_is_provided(String noteTitle) {

        assertThat(note).isNotNull();
        assertThat(note.getNoteTitle()).isEqualTo(noteTitle);

    }

    @Given("An existing application {string} with existing notes {string} and {string}")
    public void an_existing_application_with_existing_notes_and(String codeApplication, String noteTitle1, String noteTitle2) {

    }


    @When("User wants to get a list content with {string} and {string}")
    public void user_wants_to_get_a_list_content_with_and(String noteTitle1, String noteTitle2) {

    }

    @Then("All notes {string} and {string} are provided")
    public void all_notes_and_are_provided(String noteTitle1, String noteTitle2) {
       
    }
}
