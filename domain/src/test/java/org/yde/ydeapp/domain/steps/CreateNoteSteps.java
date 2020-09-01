package org.yde.ydeapp.domain.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateNoteSteps {

    public static final String CODE_APPLICATION = "AP00002";
    private Note newNote = null;
    private Application application;
    @Before
    public void setup() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    }
    // application du repository
//    @DataTableType
//    public ApplicationDataTable applicationDataTableEntry(Map<String, String> entry)  {
//        return new ApplicationDataTable(entry.get("codeApplication"),
//                entry.get("shortDescription"),
//                entry.get("longDescription"),
//                entry.get("IdRefogOrganization")
//        );
//    }
//
//    @DataTableType
//    public CycleDeVieDataTable cycleDeVieDataTableEntry(Map<String, String> entry)  {
//        return new CycleDeVieDataTable(entry.get("state"),
//                entry.get("dateOfCreation"),
//                entry.get("dateOfLastUpdate"),
//                entry.get("dateEndInReality")
//        );
//    }
//
//    @DataTableType
//    public ResponsableDataTable responsableDataTableEntry(Map<String, String> entry)  {
//        return new ResponsableDataTable(entry.get("uid"),
//                entry.get("firstName"),
//                entry.get("lastName")
//        );
//    }

    @DataTableType
    public NoteDataTable noteDataTableEntry(Map<String, String> entry)  {
        return new NoteDataTable(entry.get("noteTitle"),
                entry.get("noteContent"),
                entry.get("noteCreationDate")
        );
    }

    @Given("The application exist in the repository")
    public void the_application_exist_in_the_repository() {
        buildAApplication();
    }

    @Given("Existing note in the repository")
    public void Existing_note_in_the_repository(List<NoteDataTable> note) {
        //newNote = new Note(noteTitle, noteContent, LocalDate.parse(noteCreationDate, formatter));
        if (note.size() == 1) {
            noteDescCrea = note.get(0);
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        application.addNote(newNote);

    }





    @When("application manager wants to reference a new noteTitle {string} and a new noteContent {string} and a new noteCreationDate {string}")
    public void application_manager_wants_to_reference_a_new_note_title_and_a_new_note_content_and_a_new_note_creation_date(String noteTitle, String noteContent, String noteCreationDate) {

        newNote = new Note(noteTitle, noteContent, LocalDate.parse(noteCreationDate, formatter));
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
        newNote = new Note(noteTitle, noteContent, LocalDate.parse(noteCreationDate, formatter));
        application.addNote(newNote);

    }
//
    @When("application manager wants to delete a note entitled {string}")
    public void application_manager_wants_to_delete_a_note_entitled(String noteTitle) {
        application.deleteNote(noteTitle);
    }

    @Then("this note {string} is deleted")
    public void this_note_is_deleted(String noteTitle) {
        assertThat(application.retrieveNoteByTitle(noteTitle)).isNull();

    }
//
//    // voir comment faire le refactor
//
    private ApplicationDataTable appDescCrea = null;

    private ResponsableDataTable responsableDescCrea;
    private CycleDeVieDataTable cdvDescCrea;
    private DateTimeFormatter formatter;
    private CycleDeVieDataTable cdvDescUpdate;
    private NoteDataTable noteDescCrea;

    private void buildAApplication() {
        OrganizationIdent organizationIdent = new OrganizationIdent(appDescCrea.getIdRefogOrganization(), "Organization Name");
        Personne personne = buildPersonne(responsableDescCrea);
        CycleLife cycleLife = buildCycleLife(cdvDescCrea, formatter);
        application = new Application.Builder(appDescCrea.getCodeApplication())
                .withShortDescription(appDescCrea.getShortDescription())
                .withLongDescription(appDescCrea.getLongDescription())
                .withResponsable(personne)
                .withOrganization(organizationIdent)
                .withCycleLife(cycleLife)
                .build();
    }

    private static Personne buildPersonne(ResponsableDataTable responsableDesc) {
        return new Personne(responsableDesc.getUid(), responsableDesc.getFirstName(), responsableDesc.getLastName());
    }

    private static CycleLife buildCycleLife(CycleDeVieDataTable cdvDesc, DateTimeFormatter formatter) {

        LocalDate dateCreation=null;
        LocalDate dateOfLastUpdate=null;
        LocalDate dateEndInReality=null;

        if (cdvDesc.getDateOfCreation() !=null) {

            dateCreation = LocalDate.parse(cdvDesc.getDateOfCreation(), formatter);
        }
        if (cdvDesc.getDateOfLastUpdate() != null) {

            dateOfLastUpdate = LocalDate.parse(cdvDesc.getDateOfLastUpdate(), formatter);
        }
        if (cdvDesc.getDateEndInReality() != null) {


            dateEndInReality = LocalDate.parse(cdvDesc.getDateEndInReality(), formatter);
        }

        return new CycleLife(cdvDesc.getState(),
                dateCreation,
                dateOfLastUpdate,
                dateEndInReality);
    }

    private static Note buildNote(NoteDataTable noteDesc) {
        return new Note(noteDesc.getNoteTitle(),
                noteDesc.getNoteContent(),
                LocalDate.parse(noteDesc.getNoteCreationDate()));
    }
}
