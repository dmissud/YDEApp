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
    private ApplicationDataTable appDescCrea = null;
    private ApplicationDataTable appDescCreaUpdate = null;
    private ApplicationDataTable appDescUpdate = null;
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
            appDescCrea = apps.get(0);
            application = null;
        } else {
            throw new PendingException("Bad use of Cucumber scenario: Create a new Application");
        }
        Personne personne = new Personne(appDescCrea.getUid(), appDescCrea.getFisrtName(), appDescCrea.getLastName());
        application = new Application.Builder(appDescCrea.getCodeApplication())
            .withShortDescription(appDescCrea.getShortDescription())
            .withLongDescription(appDescCrea.getLongDescription())
            .withResponsable(personne)
            .build();
    }

    @Then("the create is success")
    public void the_create_is_success() {
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(appDescCrea.getCodeApplication());
        assertThat(application.getShortDescription()).isEqualTo(appDescCrea.getShortDescription());
        assertThat(application.getLongDescription()).isEqualTo(appDescCrea.getLongDescription());
        assertThat(application.getResponsable().getUid()).isEqualTo(appDescCrea.getUid());
        assertThat(application.getResponsable().getFirstName()).isEqualTo(appDescCrea.getFisrtName());
        assertThat(application.getResponsable().getLastName()).isEqualTo(appDescCrea.getLastName());

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

    @Given("The application exist in the repository")
    public void the_application_exist_in_the_repository(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            appDescCreaUpdate = apps.get(0);
            application = null;
        } else {
            throw new PendingException("Bad use of Cucumber scenario: create a new Application");
        }
        Personne personne = new Personne(appDescCreaUpdate.getUid(), appDescCreaUpdate.getFisrtName(), appDescCreaUpdate.getLastName());
        application = new Application.Builder(appDescCreaUpdate.getCodeApplication())
                .withShortDescription(appDescCreaUpdate.getShortDescription())
                .withLongDescription(appDescCreaUpdate.getLongDescription())
                .withResponsable(personne)
                .build();


    }



    @When("Administrator want to update an application with the following attributes")
    public void administrator_want_to_update_an_application_with_the_following_attributes(List<ApplicationDataTable> apps) {
        if (apps.size() == 1) {
            appDescUpdate = apps.get(0);

        } else {
            throw new PendingException("Bad use of Cucumber scenario: update a new Application");
        }
        Personne personne = new Personne(appDescUpdate.getUid(), appDescUpdate.getFisrtName(), appDescUpdate.getLastName());
        application.setShortDescription(appDescUpdate.getShortDescription());
        application.setLongDescription(appDescUpdate.getLongDescription());
        application.setResponsable(personne);


    }
    @Then("the update is success")
    public void the_update_is_success() {
        // Write code here that turns the phrase above into concrete actions
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(appDescUpdate.getCodeApplication());
        assertThat(application.getShortDescription()).isEqualTo(appDescUpdate.getShortDescription());
        assertThat(application.getLongDescription()).isEqualTo(appDescUpdate.getLongDescription());
        assertThat(application.getResponsable().getUid()).isEqualTo(appDescUpdate.getUid());
        assertThat(application.getResponsable().getFirstName()).isEqualTo(appDescUpdate.getFisrtName());
        assertThat(application.getResponsable().getLastName()).isEqualTo(appDescUpdate.getLastName());
    }


}
