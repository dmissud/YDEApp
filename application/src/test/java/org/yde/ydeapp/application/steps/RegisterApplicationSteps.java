package org.yde.ydeapp.application.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase.ReferenceApplicationCmd;
import org.yde.ydeapp.application.in.StateCmdEnum;
import org.yde.ydeapp.application.service.ApplicationManagementService;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.CycleLife;
import org.yde.ydeapp.domain.OrganizationIdent;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@CucumberContextConfiguration
public class RegisterApplicationSteps {

    public static final String NAME_ORGANIZATION = "NAME ORGANIZATION";
    @InjectMocks
    private ApplicationManagementService applicationManagementService;

    @Mock
    private RepositoryOfApplication repositoryOfApplication;

    @Mock
    private RepositoryOfOrganization repositoryOfOrganization;


    private StateCmdEnum stateCmdEnum;
    private Application application;
    SimpleDateFormat formatter = new SimpleDateFormat(("dd/MM/yyyy"));
    private ReferenceApplicationCmd.CycleLifeCmd cycleLifeCmd;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }



    @DataTableType
    public ReferenceApplicationCmd applicationDataTableEntry(Map<String, String> entry) throws ParseException {
        cycleLifeCmd = new ReferenceApplicationCmd.CycleLifeCmd(entry.get("state"),
                formatter.parse(entry.get("dateOfCreation")),
                formatter.parse(entry.get("dateOfLastUpdate")),
                formatter.parse(entry.get("dateEndInReality")));

        return new ReferenceApplicationCmd(entry.get("codeApplication"),
                    entry.get("idRefog"),
                    entry.get("longDescription"),
                    new ReferenceApplicationCmd.ResponsableCmd(entry.get("uid"), entry.get("firstName"), entry.get("lastName")),
                entry.get("idRefogMoe"),
                cycleLifeCmd);
    }


    @Given("Application with code {string} is not on the repository")
    public void application_with_code_is_not_on_the_repository(String codeApps) {
        Mockito
            .when(repositoryOfApplication.retrieveByAppCode(any(String.class)))
            .thenReturn(null);
    }




    @Given("organization {string} is in the directory")
    public void organization_is_in_the_directory(String idRefog) {
        OrganizationIdent organizationIdent = new OrganizationIdent(idRefog, NAME_ORGANIZATION);
        Mockito
            .when(repositoryOfOrganization.retriveIdentByIdRefog(idRefog))
            .thenReturn(organizationIdent);
    }


    @When("The administrator enrich the repository with this application with this data")
    public void the_administrator_enrich_the_repository_with_this_application_with_this_data(List<ReferenceApplicationCmd> apps) {

        if (apps.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }

        stateCmdEnum = applicationManagementService.referenceOrUpdateApplication(apps.get(0));

    }


    @Then("a new application is in the repository with code {string} rattached to the Organization with idRefog {string}")
    public void a_new_application_is_in_the_repository_with_code(String codeApp, String idRefog) {
        Mockito.verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(codeApp);
        Mockito.verify(repositoryOfOrganization, Mockito.times(1)).retriveIdentByIdRefog(idRefog);

        assertThat(stateCmdEnum).isEqualByComparingTo(StateCmdEnum.REFERENCE);
    }

    @Given("Application with code {string} is in the repository with this data")
    public void application_with_code_is_in_the_repository_with_this_data(String codeApp, List<ReferenceApplicationCmd> apps) {
        OrganizationIdent organizationIdent = new OrganizationIdent(apps.get(0).getIdRefOrganizationMoe(), NAME_ORGANIZATION);
        Personne personne = new Personne(apps.get(0).getUid(), apps.get(0).getFirstName(), apps.get(0).getLastName());
        CycleLife cycleLife = new CycleLife(apps.get(0).getState(),apps.get(0).getDateOfCreation(),apps.get(0).getDateOfLastUpdate(),apps.get(0).getDateEndInReality());
        application = new Application.Builder(apps.get(0).getCodeApp())
            .withShortDescription(apps.get(0).getShortDescription())
            .withLongDescription(apps.get(0).getLongDescription())
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .build();

        Mockito
            .when(repositoryOfApplication.retrieveByAppCode(codeApp))
            .thenReturn(application);
    }

    @When("The administrator update the repository with this application with this data")
    public void the_administrator_update_the_repository_with_this_application_with_this_data(List<ReferenceApplicationCmd> apps) {
        if (apps.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: update a Application");
        }

        stateCmdEnum = applicationManagementService.referenceOrUpdateApplication(apps.get(0));
    }

    @Then("The application with code {string} is updated in the repository")
    public void the_application_with_code_is_updated_in_the_repository(String codeApp) {
        Mockito.verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(codeApp);

        assertThat(stateCmdEnum).isEqualByComparingTo(StateCmdEnum.UPDATE);
    }

    @Then("The application with code {string} rattached to the Organization with idRefog {string}")
    public void the_application_with_code_rattached_to_the_organization_with_id_refog(String codeApp, String idRefog) {
        Mockito.verify(repositoryOfOrganization, Mockito.times(1)).retriveIdentByIdRefog(idRefog);
        assertThat(application.getOrganizationIdent()).isNotNull();
        assertThat(application.getOrganizationIdent().getIdRefog()).isEqualTo(idRefog);
    }


    @When("cycle Life is equal to")
    public void cycle_life_is_equal_to(List<ReferenceApplicationCmd> apps) {
        if (apps.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }

        stateCmdEnum = applicationManagementService.referenceOrUpdateApplication(apps.get(0));
    }

}
