package org.yde.ydeapp.domain.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.CycleLife;
import org.yde.ydeapp.domain.OrganizationIdent;
import org.yde.ydeapp.domain.Personne;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateApplicationSteps {

    public static final String CODE_APPLICATION = "AP00002";

    private ApplicationDataTable appDescCrea = null;
    private ApplicationDataTable appDescUpdate = null;
    private Application application;
    private ResponsableDataTable responsableDescCrea;
    private CycleDeVieDataTable cdvDescCrea;
    private DateTimeFormatter formatter;
    private CycleDeVieDataTable cdvDescUpdate;

    @Before
    public void setup() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    }

    @DataTableType
    public ApplicationDataTable applicationDataTableEntry(Map<String, String> entry)  {
        return new ApplicationDataTable(entry.get("codeApplication"),
            entry.get("shortDescription"),
            entry.get("longDescription"),
            entry.get("IdRefogOrganization")
        );
    }

    @DataTableType
    public CycleDeVieDataTable cycleDeVieDataTableEntry(Map<String, String> entry)  {
        return new CycleDeVieDataTable(entry.get("state"),
            entry.get("dateOfCreation"),
            entry.get("dateOfLastUpdate"),
            entry.get("dateEndInReality")
        );
    }

    @DataTableType
    public ResponsableDataTable responsableDataTableEntry(Map<String, String> entry)  {
        return new ResponsableDataTable(entry.get("uid"),
            entry.get("firstName"),
            entry.get("lastName")
        );
    }

    @Given("The application doesn't exist")
    public void the_application_doesn_t_exist() {
        appDescCrea = null;
        appDescUpdate = null;
        cdvDescCrea = null;
        cdvDescUpdate = null;
        responsableDescCrea = null;
        application = null;
    }

    @When("Administrator with the following Application attributes")
    public void administrator_with_the_following_application_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            appDescCrea = apps.get(0);
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }

    @When("With Responsable")
    public void responsable(List<ResponsableDataTable> resps) {
        if (resps.size() == 1) {
            responsableDescCrea = resps.get(0);
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }

    @When("With the cycle life")
    public void with_the_cycle_life(List<CycleDeVieDataTable> cdvs) {
        if (cdvs.size() == 1) {
            cdvDescCrea = cdvs.get(0);
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }

    @When("Administrator want to create a new application")
    public void administrator_want_to_create_a_new_application() {
        buildAApplication();
    }


    @Then("The create of a new application is a success")
    public void the_create_is_success() {
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(appDescCrea.getCodeApplication());
        assertThat(application.getShortDescription()).isEqualTo(appDescCrea.getShortDescription());
        assertThat(application.getLongDescription()).isEqualTo(appDescCrea.getLongDescription());
        assertThat(application.getResponsable()).isNotNull();
        assertThat(application.getResponsable().getUid()).isEqualTo(responsableDescCrea.getUid());
        assertThat(application.getResponsable().getFirstName()).isEqualTo(responsableDescCrea.getFirstName());
        assertThat(application.getResponsable().getLastName()).isEqualTo(responsableDescCrea.getLastName());
        assertThat(application.getOrganizationIdent()).isNotNull();
        assertThat(application.getOrganizationIdent().getIdRefog()).isEqualTo(appDescCrea.getIdRefogOrganization());
        assertThat(application.getCycleLife()).isNotNull();
        assertThat(application.getCycleLife().getDateOfCreation()).isEqualTo(LocalDate.parse(cdvDescCrea.getDateOfCreation(), formatter));
        assertThat(application.getCycleLife().getDateOfLastUpdate()).isEqualTo(LocalDate.parse(cdvDescCrea.getDateOfLastUpdate(), formatter));
        if (application.getCycleLife().getDateEndInReality() != null) {

            assertThat(application.getCycleLife().getDateEndInReality()).isEqualTo(LocalDate.parse(cdvDescCrea.getDateEndInReality(), formatter));
        }
        else {
            assertThat(cdvDescCrea.getDateEndInReality()==null);
        }
    }

    @When("Administrator want to create a new application with only code app AP00002")
    public void administrator_want_to_create_a_new_application_with_only_code_app_ap00002() {
        application = new Application.Builder(CODE_APPLICATION)
            .build();
    }

    @Then("the create is success with default field")
    public void the_create_is_success_with_default_field() {
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(CODE_APPLICATION);
    }

    @Given("The following application attributes")
    public void the_following_application_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            appDescCrea = apps.get(0);
            application = null;
        } else {
            throw new PendingException("Bad use of Cucumber scenario: create a new Application");
        }
    }

    @Given("The application exist")
    public void the_application_exist() {
        buildAApplication();
    }



    @When("Administrator want to update an application with the following attributes")
    public void administrator_want_to_update_an_application_with_the_following_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            appDescUpdate = apps.get(0);
        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }
        OrganizationIdent organizationIdent = new OrganizationIdent(appDescUpdate.getIdRefogOrganization(), "Orga Name");
        application.updateShortDescription(appDescUpdate.getShortDescription());
        application.updateLongDescription(appDescUpdate.getLongDescription());
        application.updateOrganization(organizationIdent);
    }

    @Then("the update is success")
    public void the_update_is_success() {
        // Write code here that turns the phrase above into concrete actions
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(appDescUpdate.getCodeApplication());
        assertThat(application.getShortDescription()).isEqualTo(appDescUpdate.getShortDescription());
        assertThat(application.getLongDescription()).isEqualTo(appDescUpdate.getLongDescription());
        assertThat(application.getOrganizationIdent()).isNotNull();
        assertThat(application.getOrganizationIdent().getIdRefog()).isEqualTo(appDescUpdate.getIdRefogOrganization());
    }

    @When("Administrator want to update an application with the cycle life")
    public void administrator_want_to_update_an_application_with_the_cycle_life(List<CycleDeVieDataTable> cdvs)  {
        if (cdvs.size() == 1) {
            cdvDescUpdate = cdvs.get(0);
        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }

        CycleLife cycleLife = buildCycleLife(cdvDescUpdate, formatter);
        application.updateCycleLife(cycleLife);
    }

    @Then("the update of cycleLife is success")
    public void the_update_of_cycle_life_is_success()  {
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(appDescCrea.getCodeApplication());
        assertThat(application.getCycleLife()).isNotNull();
        assertThat(application.getCycleLife().getState()).isEqualTo(cdvDescUpdate.getState());
        assertThat(application.getCycleLife().getDateOfCreation()).isEqualTo(LocalDate.parse(cdvDescUpdate.getDateOfCreation(), formatter));
        assertThat(application.getCycleLife().getDateOfLastUpdate()).isEqualTo(LocalDate.parse(cdvDescUpdate.getDateOfLastUpdate(), formatter));
        assertThat(application.getCycleLife().getDateEndInReality()).isEqualTo(LocalDate.parse(cdvDescUpdate.getDateEndInReality(), formatter));
    }


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



}
