package org.yde.ydeapp.application.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.yde.ydeapp.application.in.ReferenceNoteUseCase;
import org.yde.ydeapp.application.service.NoteManagementService;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.Note;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.List;
import java.util.Map;


public class RegisterNoteSteps {

    @InjectMocks
    NoteManagementService noteManagementService;

    @Mock
    private RepositoryOfApplication repositoryOfApplication;

    private Application application;
    private ReferenceNoteUseCase.ReferenceNoteCmd note;

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
        application = repositoryOfApplication.retrieveByAppCode(codeApplication);

    }


    @When("User wants to reference a new note with following datas")
    public void user_wants_to_reference_a_new_note_with_following_datas(List<ReferenceNoteUseCase.ReferenceNoteCmd> notes) {
        if (notes.size() == 1) {
            note = notes.get(0);

        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }

        application.setShortDescription(note.getShortDescription());
        application.setLongDescription(note.getLongDescription());
        application.setResponsable(personne);
    }
    @Then("new note is referenced to application code {string} with title {string}")
    public void new_note_is_referenced_to_application_code_with_title(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}
