package org.yde.ydeapp.domain.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.Application;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateApplicationSteps {

    public static final String CODE_APPLICATION = "AP00002";
    private ApplicationDataTable appDesc = null;
    private Application application;
    @DataTableType
    public ApplicationDataTable applicationFataTableEntry(Map<String, String> entry) {
        application = null;
        return new ApplicationDataTable(entry.get("codeApplication"),
            entry.get("shortDescription"),
            entry.get("longDescription"),
            entry.get("nameOfResponsable"));
    }


    @Given("Administrator want to create a new application with the following attributes")
    public void administrator_want_to_create_a_new_application_with_the_following_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            appDesc = apps.get(0);
            application = null;
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
    }

    @When("Administrator validate")
    public void administrator_validate() {
        Application.Builder appBuilder = new Application.Builder(appDesc.getCodeApplication());
        if (appDesc.getShortDescription() != null) {
            appBuilder.withShortDescription(appDesc.getShortDescription());
        }
        if (appDesc.getLongDescription() != null) {
            appBuilder.withLongDescription(appDesc.getLongDescription());
        }
        if (appDesc.getNameOfResponsable() != null) {
            appBuilder.withResponsable(appDesc.getNameOfResponsable());
        }
        application = appBuilder.build();
    }

    @Then("the create is success")
    public void the_create_is_success() {
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(appDesc.getCodeApplication());
    }

    @Given("Administrator want to create a new application with only code app AP00002")
    public void administrator_want_to_create_a_new_application_with_only_code_app_ap00002() {
        appDesc = new ApplicationDataTable(CODE_APPLICATION, null, null, null);
    }

    @Then("the create is success with default field")
    public void the_create_is_success_with_default_field() {
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(CODE_APPLICATION);
    }
}
