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
import org.yde.ydeapp.domain.application.*;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class RegisterNoteSteps {

    @InjectMocks
    NoteManagementService noteManagementService;

    @Mock
    private RepositoryOfApplication repositoryOfApplication;

    private Application application;
    private EntityNotFound applicationNotFound = null;
    private EntityNotFound exceptionNoteNotFound = null;
    private Note note = null;
    private Map<String, Note> notes = new HashMap<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataTableType
    public ReferenceNoteUseCase.ReferenceNoteCmd noteDataTableEntry(Map<String, String> entry) {
        return new ReferenceNoteUseCase.ReferenceNoteCmd(
            entry.get("noteTitle"),
            entry.get("noteContent"),
            LocalDate.parse(entry.get("noteCreationDate"), formatter));
    }

    @Given("Application referenced as {string} in repository")
    public void application_referenced_as_in_repository(String codeApplication) {
        buildApplication(codeApplication);
        Mockito
            .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
            .thenReturn(application);

    }


    @When("User wants to reference a new note with following data")
    public void user_wants_to_reference_a_new_note_with_following_data(List<ReferenceNoteUseCase.ReferenceNoteCmd> notes) {
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
    }

    @When("User wants to reference a new note to application {string} with following data")
    public void user_wants_to_reference_a_new_note_to_application_with_following_data(String codeApplication, List<ReferenceNoteUseCase.ReferenceNoteCmd> notes) {
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
        buildApplication(codeApplication);
        note = new Note(noteTitle, noteContent, LocalDate.parse(noteCreationDate, formatter));
        application.addNote(note);
        Mockito
            .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
            .thenReturn(application);

    }

    @When("User wants to update note {string} with data")
    public void user_wants_to_update_note_with_data(String noteTitle, List<ReferenceNoteUseCase.ReferenceNoteCmd> notes) {
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
    }

    @Given("An existing application {string} with an existing note {string}, {string}, {string}")
    public void an_existing_application(String codeApplication, String noteTitle, String noteContent, String noteCreationDate) {
        buildApplication(codeApplication);
        note = new Note(noteTitle, noteContent, LocalDate.parse(noteCreationDate, formatter));
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

    @Given("An existing application {string} with existing notes {string}, {string}, {string} and {string}, {string}, {string}")
    public void an_existing_application_with_existing_notes_and(String codeApplication, String noteTitle1, String noteContent1, String noteCreationDate1,
                                                                String noteTitle2, String noteContent2, String noteCreationDate2) {
        buildApplication(codeApplication);
        Note note1 = new Note(noteTitle1, noteContent1, LocalDate.parse(noteCreationDate1, formatter));
        Note note2 = new Note(noteTitle2, noteContent2, LocalDate.parse(noteCreationDate2, formatter));
        application.addNote(note1);
        application.addNote(note2);
    }

    @When("User wants to get a note-list")
    public void user_wants_to_get_a_note_list() {
        notes = application.retrieveNotes();
    }

    @Then("All notes {string} and {string} are provided")
    public void all_notes_and_are_provided(String noteTitle1, String noteTitle2) {
        assertThat(notes).isNotNull();
        assertThat(notes.get(noteTitle1).getNoteTitle()).isEqualTo(noteTitle1);
        assertThat(notes.get(noteTitle2).getNoteTitle()).isEqualTo(noteTitle2);
    }

    @Given("An existing application {string} with the note {string}, {string}, {string}")
    public void an_existing_application_with_the_note(String codeApplication, String noteTitle, String noteContent, String noteCreationDate) {
        buildApplication(codeApplication);
        note = new Note(noteTitle, noteContent, LocalDate.parse(noteCreationDate, formatter));
        application.addNote(note);
        Mockito
            .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
            .thenReturn(application);
    }

    @When("User wants to delete a note entitled {string}")
    public void user_wants_to_delete_a_note_entitled(String noteTitle) {
        noteManagementService.deleteNoteByTitle(application.getCodeApplication(), noteTitle);
    }

    @Then("{string} is deleted")
    public void note_is_deleted(String noteTitle) {
        Mockito
            .verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(application.getCodeApplication());
        assertThat(application.retrieveNoteByTitle(noteTitle)).isNull();
    }

    // Steps of scenario deleting an unknown note
    @Given("An existing application {string} with following note {string}, {string}, {string}")
    public void an_existing_application_with_following_note(String codeApplication, String noteTitle, String noteContent, String noteCreationDate) {
        buildApplication(codeApplication);
        note = new Note(noteTitle, noteContent, LocalDate.parse(noteCreationDate, formatter));
        application.addNote(note);
        Mockito
            .when(repositoryOfApplication.retrieveByAppCode(codeApplication))
            .thenReturn(application);
    }

    @When("User wants to delete a note named as {string} to the application {string}")
    public void user_wants_to_delete_note_named(String noteTitle2, String codeApp) {
        exceptionNoteNotFound = null;
        try {
            noteManagementService.deleteNoteByTitle(codeApp, noteTitle2);
        } catch (EntityNotFound entityNotFound) {
            exceptionNoteNotFound = entityNotFound;
        }
    }

    @Then("Exception EntityNotFound is thrown for {string}")
    public void exception_entity_not_found_is_thrown(String noteTitle2) {
        Mockito
            .verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(application.getCodeApplication());
        Mockito
            .verify(repositoryOfApplication, Mockito.times(0)).updateApplication(application);

        assertThat(exceptionNoteNotFound).isNotNull();
        assertThat(exceptionNoteNotFound.getMessage()).isEqualTo(String.format("Note %s does not exist", noteTitle2));
    }

    private void buildApplication(String codeApplication) {
        OrganizationIdent organizationIdent = new OrganizationIdent("12345678", "Organization Name");
        Personne personne = new Personne("123456", "firstName", "lastName");
        CycleLife cycleLife = new CycleLife("Active", LocalDate.now(), LocalDate.now(),LocalDate.now());
        ItSolution itSolution= new ItSolution("Open","","ibm");
        Criticity criticity = new Criticity("oui","non","c12","service minimum", "00 j 12h 12 min","05 j 11h 55min");
        application = new Application.Builder(codeApplication)
            .withShortDescription("Short description")
            .withLongDescription("Long description Long description")
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
            .build();
    }
}
