package org.yde.ydeapp.domain.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.application.Note;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateNoteSteps {

    private final ScenarioContext scenarioContext;

    public CreateNoteSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before
    public void setup() {

    }


    @DataTableType
    public NoteDataTable noteDataTableEntry(Map<String, String> entry)  {
        return new NoteDataTable(entry.get("noteTitle"),
                entry.get("noteContent"),
                entry.get("noteCreationDate")
        );
    }

    @Given("The application exist in the repository")
    public void the_application_exist_in_the_repository() {
        this.scenarioContext.buildAApplication();
    }

    @Given("a user consult the application")
    public void a_user_consult_the_application() {
    }

    @Given("Existing note in the repository")
    public void Existing_note_in_the_repository(List<NoteDataTable> note) {
        addNoteToApplication(note);
    }

    @When("The user wants to reference a new note")
    public void application_manager_wants_to_reference_a_new_note_title_and_a_new_note_content_and_a_new_note_creation_date(List<NoteDataTable> note) {
        addNoteToApplication(note);
    }

    @Then("note reference succeeded with")
    public void note_reference_succeeded_with_a_new_note_title_and_a_new_note_content_and_a_new_note_creation_date(List<NoteDataTable> note) {
        if (note.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Note");
        }
        assertThat(this.scenarioContext.getApplication().retrieveNotes())
            .isNotNull();
        assertThat(this.scenarioContext.getApplication().retrieveNotes().get(note.get(0).getNoteTitle()))
            .isNotNull();
        assertThat(this.scenarioContext.getApplication().retrieveNotes().get(note.get(0).getNoteTitle()).getNoteTitle())
            .isEqualTo(note.get(0).getNoteTitle());
        assertThat(this.scenarioContext.getApplication().retrieveNotes().get(note.get(0).getNoteTitle()).getNoteContent())
            .isEqualTo(note.get(0).getNoteContent());
        assertThat(this.scenarioContext.getApplication().retrieveNotes().get(note.get(0).getNoteTitle()).getNoteCreationDate())
            .isEqualTo(LocalDate.parse(note.get(0).getNoteCreationDate(), this.scenarioContext.getFormatter()));
    }

    @Given("existing note in the repository")
    public void existing_code_application_and_existing_note_in_the_repository(List<NoteDataTable> note) {
        addNoteToApplication(note);
    }

    private void addNoteToApplication(List<NoteDataTable> note) {
        if (note.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Note");
        }
        Note newNote = new Note(note.get(0).getNoteTitle(),
            note.get(0).getNoteContent(),
            LocalDate.parse(note.get(0).getNoteCreationDate(), this.scenarioContext.getFormatter()));
        this.scenarioContext.getApplication().storeOfNote(newNote);
    }

    @When("the user wants to delete a note entitled {string}")
    public void application_manager_wants_to_delete_a_note_entitled(String noteTitle) {
        this.scenarioContext.getApplication().deleteNote(noteTitle);
    }

    @Then("this note {string} is deleted")
    public void this_note_is_deleted(String noteTitle) {
        assertThat(this.scenarioContext.getApplication().retrieveNoteByTitle(noteTitle)).isNull();
    }


}
