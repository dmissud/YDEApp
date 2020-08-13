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
import org.yde.ydeapp.application.service.ApplicationManagementService;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@CucumberContextConfiguration
public class RegisterApplicationSteps {

    @InjectMocks
    private ApplicationManagementService applicationManagementService;

    @Mock
    private RepositoryOfApplication repositoryOfApplication;



    private Application application;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataTableType
    public ReferenceApplicationCmd applicationFataTableEntry(Map<String, String> entry) {
        return new ReferenceApplicationCmd(entry.get("codeApplication"),
                entry.get("shortDescription"),
                entry.get("longDescription"),
                entry.get("uid"),
                entry.get("firstName"),
                entry.get("lastName"));

    }

    @Given("Application with code {string} is not on the repository")
    public void application_with_code_is_not_on_the_repository(String codeApp) {
        Mockito
                .when(repositoryOfApplication.retrieveByAppCode(any(String.class)))
                .thenThrow(new EntityNotFound(String.format("Application : %s", codeApp)));
    }

    @When("The administrator enrich the repository with this application with this data")
    public void the_administrator_enrich_the_repository_with_this_application_with_this_data(List<ReferenceApplicationCmd> apps) {
        if (apps.size() != 1) {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        application = applicationManagementService.referenceApplication(apps.get(0));
    }

    @Then("a new application is in the repository with code {string}")
    public void a_new_application_is_in_the_repository_with_code(String string) {
        Mockito.verify(repositoryOfApplication, Mockito.times(1)).retrieveByAppCode(string);


    }



}
