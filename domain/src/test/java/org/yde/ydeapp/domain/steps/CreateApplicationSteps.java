package org.yde.ydeapp.domain.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.Personne;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateApplicationSteps {

    public static final String CODE_APPLICATION = "AP00002";
    private ApplicationDataTable appDesc = null;
    private Application application;


    @DataTableType
    public ApplicationDataTable applicationFataTableEntry(Map<String, String> entry) {
        return new ApplicationDataTable(entry.get("codeApplication"),
            entry.get("shortDescription"),
            entry.get("longDescription"),
            entry.get("uid"),
            entry.get("firstName"),
            entry.get("lastName"));

    }

    @Given("The application doesn't exist in the repository")
    public void the_application_doesn_t_exist_in_the_repository() {
        application = null;
    }

    @When("Administrator want to create a new application with the following attributes")
    public void administrator_want_to_create_a_new_application_with_the_following_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            appDesc = apps.get(0);
            application = null;
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        Personne personne = new Personne(appDesc.getUid(), appDesc.getFisrtName(), appDesc.getLastName());
        application = new Application.Builder(appDesc.getCodeApplication())
            .withShortDescription(appDesc.getShortDescription())
            .withLongDescription(appDesc.getLongDescription())
            .withResponsable(personne)
            .build();
    }

    @Then("the create is success")
    public void the_create_is_success() {
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(appDesc.getCodeApplication());
        assertThat(application.getShortDescription()).isEqualTo(appDesc.getShortDescription());
        assertThat(application.getLongDescription()).isEqualTo(appDesc.getLongDescription());
        assertThat(application.getResponsable().getUid()).isEqualTo(appDesc.getUid());
        assertThat(application.getResponsable().getFirstName()).isEqualTo(appDesc.getFisrtName());
        assertThat(application.getResponsable().getLastName()).isEqualTo(appDesc.getLastName());

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
}
