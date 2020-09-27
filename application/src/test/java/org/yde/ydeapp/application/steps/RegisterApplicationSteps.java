package org.yde.ydeapp.application.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase.ReferenceApplicationCmd;
import org.yde.ydeapp.domain.flux.StateUpdateEnum;
import org.yde.ydeapp.application.service.ApplicationManagementService;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    private StateUpdateEnum stateUpdateEnum;
    private ReferenceApplicationCmd application;
    private DateTimeFormatter formatter;
    private ApplicationDataTableCmd appDescCrea = null;
    private ApplicationDataTableCmd appDescUpdate = null;
    private ResponsableDataTableCmd responsableDescCrea;
    private ResponsableDataTableCmd responsableDescUpdate;
    private CycleLifeDataTablecmd cdvDescCrea;
    private CycleLifeDataTablecmd cdvDescUpdate;
    private ItSolutionDataTableCmd itsDesCrea;
    private ItSolutionDataTableCmd itsDesUpdate;
    private OrganizationIdent organizationIdent;

    @Before
    public void setupdate() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    }



    @Before
    public void setup() {MockitoAnnotations.initMocks(this);
    }

    @DataTableType
    public ApplicationDataTableCmd applicationDataTableEntry(Map<String, String> entry)  {
        return new ApplicationDataTableCmd(entry.get("codeApplication"),
                entry.get("shortDescription"),
                entry.get("longDescription"),
                entry.get("IdRefogOrganization")
        );
    }

    @DataTableType
    public CycleLifeDataTablecmd cycleLifeDataTableEntry(Map<String, String> entry) {
        return new CycleLifeDataTablecmd(entry.get("state"),
                entry.get("dateOfCreation"),
                entry.get("dateOfLastUpdate"),
                entry.get("dateEndInReality")
        );
    }

    @DataTableType
    public ItSolutionDataTableCmd itSolutionDataTableCmd(Map<String, String> entry) {
        return new ItSolutionDataTableCmd(entry.get("typeOfSolution"),
                entry.get("nameOfFirmware"),
                entry.get("labelOfSourcingMode")
        );
    }

    @DataTableType
    public ResponsableDataTableCmd responsableDataTableEntry(Map<String, String> entry)  {
        return new ResponsableDataTableCmd(entry.get("uid"),
                entry.get("firstName"),
                entry.get("lastName")
        );
    }
    @Given("Application with code {string} is not on the repository")
    public void application_with_code_is_not_on_the_repository(String codApp) {
        Mockito
                .when(repositoryOfApplication.retrieveByAppCode(any(String.class)))
                .thenReturn(null);
    }

    @When("The administrator enrich the repository with this application with this data")
    public void the_administrator_enrich_the_repository_with_this_application_with_this_data(List<ApplicationDataTableCmd> apps) {

        if (apps.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        appDescCrea = apps.get(0);
    }
    @When("With Responsable create")
    public void with_responsable_create(List<ResponsableDataTableCmd> resp) {
        if (resp.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        responsableDescCrea=resp.get(0);

    }
    @When("With the cycle life create")
    public void with_the_cycle_life_create(List<CycleLifeDataTablecmd> cycLif) {
        if (cycLif.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        cdvDescCrea =cycLif.get(0);
        //buildAnApplication();

        //stateUpdateEnum = applicationManagementService.referenceOrUpdateApplication(application);
    }
    @When("With it solution create")
    public void with_the_it_solution_create(List<ItSolutionDataTableCmd> itss) {
        if (itss.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        itsDesCrea =itss.get(0);
        buildAnApplication();

        stateUpdateEnum = applicationManagementService.referenceOrUpdateApplication(application);
    }



    @Then("The application with code {string} is created in the repository")
    public void the_application_with_code_is_created_in_the_repository(String codeApp) {

        Mockito.verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(codeApp);
    }




    @Given("The following application attributes")
    public void the_following_application_attributes(List<ApplicationDataTableCmd> apps) {
        if (apps.size() == 1) {
            appDescCrea = apps.get(0);
            application = null;
        } else {
            throw new PendingException("Bad use of Cucumber scenario: create a new Application");
        }
    }

    @Given("Application is in the repository with this data")
    public void application_is_in_the_repository_with_this_data(List<ApplicationDataTableCmd> apps) {
        if (apps.size() == 1) {
            appDescCrea = apps.get(0);
            application = null;
        } else {
            throw new PendingException("Bad use of Cucumber scenario: create a new Application");
        }

    }

    @Given("With Responsable")
    public void with_responsable(List<ResponsableDataTableCmd> resp) {
        if (resp.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");

        }
        responsableDescCrea = resp.get(0);
     //   Personne personne= buildPersonne(responsableDescUpdate);

    }
    @Given("With the cycle life")
    public void with_the_cycle_life(List<CycleLifeDataTablecmd> cycLif) {
        if (cycLif.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        cdvDescCrea = cycLif.get(0);
       // buildAnApplication();
       // CycleLife cycleLife = buildCycleLife(cdvDescUpdate, formatter) ;


    }
    @When("With the it solution")
    public void with_the_it_solution(List<ItSolutionDataTableCmd> itss) {
        if (itss.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        itsDesCrea = itss.get(0);
        buildAnApplication();

      //  stateCmdEnum = applicationManagementService.referenceOrUpdateApplication(application);
    }
    @Given("control organization {string} is in the directory")
    public void control_organization_is_in_the_directory(String idRefog) {
        this.organizationIdent = new OrganizationIdent(idRefog, NAME_ORGANIZATION);
        Mockito
                .when(repositoryOfOrganization.retriveIdentByIdRefog(idRefog))
                .thenReturn(organizationIdent);


    }
    @Given("organization {string} is in the directory")
    public void organization_is_in_the_directory(String idRefog) {
        OrganizationIdent organizationIdent = new OrganizationIdent(idRefog, NAME_ORGANIZATION);
        Mockito
                .when(repositoryOfOrganization.retriveIdentByIdRefog(idRefog))
                .thenReturn(organizationIdent);

    }
    @When("The administrator update the repository with this application with this data")
    public void the_administrator_update_the_repository_with_this_application_with_this_data(List<ApplicationDataTableCmd> apps) {

        if (apps.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        appDescUpdate = apps.get(0);
    }
    @When("With Responsable update")
    public void with_responsable_update(List<ResponsableDataTableCmd> resp) {
        if (resp.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        responsableDescUpdate=resp.get(0);

    }
    @When("With the cycle life update")
    public void with_the_cycle_life_update(List<CycleLifeDataTablecmd> cycLif) {
        if (cycLif.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        cdvDescUpdate =cycLif.get(0);
         //CycleLife cycleLife = buildCycleLife(cdvDescUpdate, formatter) ;
         //application.updateCycleLife(cycleLife);
       // buildAnApplicationUp();

        //stateUpdateEnum = applicationManagementService.referenceOrUpdateApplication(application);
    }
    @When("With the it solution update")
    public void with_the_it_solution_update(List<ItSolutionDataTableCmd> itss) {
        if (itss.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        itsDesUpdate =itss.get(0);
        buildAnApplication();

        stateUpdateEnum = applicationManagementService.referenceOrUpdateApplication(application);
    }

    @Then("The application with code {string} is updated in the repository")
    public void the_application_with_code_is_updated_in_the_repository(String codeApp) {
        Mockito.verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(codeApp);
        assertThat(stateUpdateEnum).isEqualByComparingTo(StateUpdateEnum.REFERENCE);
    }

    @Then("The application with code {string} rattached to the Organization with idRefog {string}")
    public void the_application_with_code_rattached_to_the_organization_with_id_refog(String codeApp, String idRefog) {
        Mockito.verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(codeApp);
        Mockito.verify(repositoryOfOrganization, Mockito.times(1)).retriveIdentByIdRefog(idRefog);

        assertThat(stateUpdateEnum).isEqualByComparingTo(StateUpdateEnum.REFERENCE);
    }

    private void buildAnApplicationUp() {
        OrganizationIdent organizationIdent = new OrganizationIdent(appDescUpdate.getIdRefogOrganization(), "Organization Name");
        ReferenceApplicationCmd.ResponsableCmd personne = buildPersonne(responsableDescUpdate);
        ReferenceApplicationCmd.CycleLifeCmd cycleLife = buildCycleLife(cdvDescUpdate, formatter);
        ReferenceApplicationCmd.ItSolutionCmd itSolution = buildItSolution(itsDesUpdate);
        application = new ReferenceApplicationCmd(appDescUpdate.getCodeApplication(),
                                    appDescUpdate.getShortDescription(),
                                    appDescUpdate.getLongDescription(),
                personne,
                appDescUpdate.getIdRefogOrganization(),
                cycleLife,
                itSolution);

    }

    private void buildAnApplication() {
        OrganizationIdent organizationIdent = new OrganizationIdent(appDescCrea.getIdRefogOrganization(), "Organization Name");
        ReferenceApplicationCmd.ResponsableCmd responsableCmd = buildPersonne(responsableDescCrea);
        ReferenceApplicationCmd.CycleLifeCmd cycleLife = buildCycleLife(cdvDescCrea, formatter);
        ReferenceApplicationCmd.ItSolutionCmd itSolution = buildItSolution(itsDesCrea);
        application = new ReferenceApplicationCmd(appDescCrea.getCodeApplication(),
                appDescCrea.getShortDescription(),
                appDescCrea.getLongDescription(),
                responsableCmd,
                appDescCrea.getIdRefogOrganization(),
                cycleLife,
                itSolution);

    }
    private static ReferenceApplicationCmd.ResponsableCmd buildPersonne(ResponsableDataTableCmd responsableDesc) {
        return new ReferenceApplicationCmd.ResponsableCmd(responsableDesc.getUid(), responsableDesc.getFirstName(), responsableDesc.getLastName());
    }

    private static ReferenceApplicationCmd.CycleLifeCmd buildCycleLife(CycleLifeDataTablecmd cdvDesc, DateTimeFormatter formatter) {
        return new ReferenceApplicationCmd.CycleLifeCmd(cdvDesc.getState(),
                LocalDate.parse(cdvDesc.getDateOfCreation(), formatter),
                LocalDate.parse(cdvDesc.getDateOfLastUpdate(), formatter),
                LocalDate.parse(cdvDesc.getDateEndInReality(), formatter));
    }

    private static ReferenceApplicationCmd.ItSolutionCmd buildItSolution(ItSolutionDataTableCmd itsDesc){
        return new ReferenceApplicationCmd.ItSolutionCmd(itsDesc.getTypeOfSolution(),
                itsDesc.getNameOfFirmware(),itsDesc.getLabelOfSourcingMode());
    }


}
