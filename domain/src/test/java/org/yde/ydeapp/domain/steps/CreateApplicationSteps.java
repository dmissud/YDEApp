package org.yde.ydeapp.domain.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.application.CycleLife;
import org.yde.ydeapp.domain.application.ItSolution;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateApplicationSteps {

    public static final String CODE_APPLICATION = "AP00002";
    private final ScenarioContext scenarioContext;

    public CreateApplicationSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before
    public void setup() {

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
    public ItSolutionDataTable itSolutionDataTable(Map<String, String> entry)  {
        return new ItSolutionDataTable(entry.get("typeOfSolution"),
                entry.get("nameOfFirmware"),
                entry.get("labelOfSourcingMode")

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
        this.scenarioContext.initScenario();
    }

    @When("Administrator with the following Application attributes")
    public void administrator_with_the_following_application_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            this.scenarioContext.setAppDescCrea(apps.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }

    @When("With Responsable")
    public void responsable(List<ResponsableDataTable> resps) {
        if (resps.size() == 1) {
            this.scenarioContext.setResponsableDescCrea(resps.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }

    @When("With the cycle life")
    public void with_the_cycle_life(List<CycleDeVieDataTable> cdvs) {
        if (cdvs.size() == 1) {
            this.scenarioContext.setCdvDescCrea(cdvs.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }
    @When("With it solution")
    public void with_it_solution(List<ItSolutionDataTable> itss) {
        if (itss.size() == 1) {
            this.scenarioContext.setItsDescCrea(itss.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }

    @When("Administrator want to create a new application")
    public void administrator_want_to_create_a_new_application() {
        this.scenarioContext.buildAApplication();
    }


    @Then("The create of a new application is a success")
    public void the_create_is_success() {
        assertThat(this.scenarioContext.getApplication())
            .isNotNull();
        assertThat(this.scenarioContext.getApplication().getCodeApplication())
            .isEqualTo(this.scenarioContext.getAppDescCrea().getCodeApplication());
        assertThat(this.scenarioContext.getApplication().getShortDescription())
            .isEqualTo(this.scenarioContext.getAppDescCrea().getShortDescription());
        assertThat(this.scenarioContext.getApplication().getLongDescription())
            .isEqualTo(this.scenarioContext.getAppDescCrea().getLongDescription());
        assertThat(this.scenarioContext.getApplication().getResponsable()).isNotNull();
        assertThat(this.scenarioContext.getApplication().getResponsable().getUid())
            .isEqualTo(this.scenarioContext.getResponsableDescCrea().getUid());
        assertThat(this.scenarioContext.getApplication().getResponsable().getFirstName())
            .isEqualTo(this.scenarioContext.getResponsableDescCrea().getFirstName());
        assertThat(this.scenarioContext.getApplication().getResponsable().getLastName())
            .isEqualTo(this.scenarioContext.getResponsableDescCrea().getLastName());
        assertThat(this.scenarioContext.getApplication().getOrganizationIdent())
            .isNotNull();
        assertThat(this.scenarioContext.getApplication().getOrganizationIdent().getIdRefog())
            .isEqualTo(this.scenarioContext.getAppDescCrea().getIdRefogOrganization());
        assertThat(this.scenarioContext.getApplication().getCycleLife())
            .isNotNull();
        assertThat(this.scenarioContext.getApplication().getCycleLife().getDateOfCreation())
            .isEqualTo(LocalDate.parse(this.scenarioContext.getCdvDescCrea().getDateOfCreation(), this.scenarioContext.getFormatter()));
        assertThat(this.scenarioContext.getApplication().getCycleLife().getDateOfLastUpdate())
            .isEqualTo(LocalDate.parse(this.scenarioContext.getCdvDescCrea().getDateOfLastUpdate(), this.scenarioContext.getFormatter()));
        if (this.scenarioContext.getApplication().getCycleLife().getDateEndInReality() != null) {

            assertThat(this.scenarioContext.getApplication().getCycleLife().getDateEndInReality()).isEqualTo(LocalDate.parse(this.scenarioContext.getCdvDescCrea().getDateEndInReality(), this.scenarioContext.getFormatter()));
        }
        else {
            assertThat(this.scenarioContext.getCdvDescCrea().getDateEndInReality()).isNull();
        }
        assertThat(this.scenarioContext.getApplication().getItSolution())
                .isNotNull();
        assertThat(this.scenarioContext.getApplication().getItSolution().getLabelOfSourcingMode())
                .isEqualTo(this.scenarioContext.getItsDescCrea().getLabelOfSourcingMode());
        assertThat(this.scenarioContext.getApplication().getItSolution().getNameOfFirmware())
                .isEqualTo(this.scenarioContext.getItsDescCrea().getNameOfFirmware());
        assertThat(this.scenarioContext.getApplication().getItSolution().getTypeOfSolution())
                .isEqualTo(this.scenarioContext.getItsDescCrea().getTypeOfSolution());

    }

    @Given("The following application attributes")
    public void the_following_application_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            this.scenarioContext.setAppDescCrea(apps.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: create a new Application");
        }
    }

    @Given("The application exist")
    public void the_application_exist() {
        this.scenarioContext.buildAApplication();
    }

    @When("Administrator want to update an application with the following attributes")
    public void administrator_want_to_update_an_application_with_the_following_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            this.scenarioContext.setAppDescUpdate(apps.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }
        OrganizationIdent organizationIdent = new OrganizationIdent(this.scenarioContext.getAppDescUpdate().getIdRefogOrganization(), "Orga Name");
        this.scenarioContext.getApplication().updateShortDescription(this.scenarioContext.getAppDescUpdate().getShortDescription());
        this.scenarioContext.getApplication().updateLongDescription(this.scenarioContext.getAppDescUpdate().getLongDescription());
        this.scenarioContext.getApplication().updateOrganization(organizationIdent);
    }

    @Then("the update is success")
    public void the_update_is_success() {
        // Write code here that turns the phrase above into concrete actions
        assertThat(this.scenarioContext.getApplication()).isNotNull();
        assertThat(this.scenarioContext.getApplication().getCodeApplication())
            .isEqualTo(this.scenarioContext.getAppDescUpdate().getCodeApplication());
        assertThat(this.scenarioContext.getApplication().getShortDescription())
            .isEqualTo(this.scenarioContext.getAppDescUpdate().getShortDescription());
        assertThat(this.scenarioContext.getApplication().getLongDescription())
            .isEqualTo(this.scenarioContext.getAppDescUpdate().getLongDescription());
        assertThat(this.scenarioContext.getApplication().getOrganizationIdent())
            .isNotNull();
        assertThat(this.scenarioContext.getApplication().getOrganizationIdent().getIdRefog())
            .isEqualTo(this.scenarioContext.getAppDescUpdate().getIdRefogOrganization());
    }

    @When("Administrator want to update an application with the cycle life")
    public void administrator_want_to_update_an_application_with_the_cycle_life(List<CycleDeVieDataTable> cdvs)  {
        if (cdvs.size() == 1) {
            this.scenarioContext.setCdvDescUpdate(cdvs.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }

        CycleLife cycleLife = this.scenarioContext.buildCycleLifeForUpdate();
        this.scenarioContext.getApplication().updateCycleLife(cycleLife);
    }

    @Then("the update of cycleLife is success")
    public void the_update_of_cycle_life_is_success()  {
        assertThat(this.scenarioContext.getApplication()).isNotNull();
        assertThat(this.scenarioContext.getApplication().getCodeApplication())
            .isEqualTo(this.scenarioContext.getAppDescCrea().getCodeApplication());
        assertThat(this.scenarioContext.getApplication().getCycleLife())
            .isNotNull();
        assertThat(this.scenarioContext.getApplication().getCycleLife().getState())
            .isEqualTo(this.scenarioContext.getCdvDescUpdate().getState());
        assertThat(this.scenarioContext.getApplication().getCycleLife().getDateOfCreation())
            .isEqualTo(LocalDate.parse(this.scenarioContext.getCdvDescUpdate().getDateOfCreation(), this.scenarioContext.getFormatter()));
        assertThat(this.scenarioContext.getApplication().getCycleLife().getDateOfLastUpdate())
            .isEqualTo(LocalDate.parse(this.scenarioContext.getCdvDescUpdate().getDateOfLastUpdate(), this.scenarioContext.getFormatter()));
        assertThat(this.scenarioContext.getApplication().getCycleLife().getDateEndInReality())
            .isEqualTo(LocalDate.parse(this.scenarioContext.getCdvDescUpdate().getDateEndInReality(), this.scenarioContext.getFormatter()));
    }

    @When("Administrator want to update an application with the ItSolution")
    public void administrator_want_to_update_an_application_with_the_ItSolution(List<ItSolutionDataTable> itss)  {
        if (itss.size() == 1) {
            this.scenarioContext.setItsDescUpdate(itss.get(0));
        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }

        ItSolution itSolution = this.scenarioContext.buildItSolutionForUpdate();
        this.scenarioContext.getApplication().updateItSolution(itSolution);
    }

    @Then("the update of itsolution is success")
    public void the_update_of_itsolution_is_success()  {
        assertThat(this.scenarioContext.getApplication()).isNotNull();
        assertThat(this.scenarioContext.getApplication().getCodeApplication())
                .isEqualTo(this.scenarioContext.getAppDescCrea().getCodeApplication());
        assertThat(this.scenarioContext.getApplication().getItSolution())
                .isNotNull();
        assertThat(this.scenarioContext.getApplication().getItSolution().getLabelOfSourcingMode())
                .isEqualTo(this.scenarioContext.getItsDescUpdate().getLabelOfSourcingMode());
        assertThat(this.scenarioContext.getApplication().getItSolution().getNameOfFirmware())
                .isEqualTo(this.scenarioContext.getItsDescUpdate().getNameOfFirmware());
        assertThat(this.scenarioContext.getApplication().getItSolution().getTypeOfSolution())
                .isEqualTo(this.scenarioContext.getItsDescUpdate().getTypeOfSolution());
    }


}
