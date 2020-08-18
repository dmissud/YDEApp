package org.yde.ydeapp.application.steps;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase.ReferenceOrganisationCmd;
import org.yde.ydeapp.application.service.OrganizationManagementService;
import org.yde.ydeapp.domain.Organization;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class RegisterOrganizationSteps {
    @InjectMocks
    private final ReferenceOrganizationUseCase referenceOrganizationUseCase = new OrganizationManagementService();

    @Mock
    private RepositoryOfOrganization repositoryOfOrganization;

    private Organization organization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataTableType
    public ReferenceOrganisationCmd organizationDataTableEntry(Map<String, String> entry) {
        return new ReferenceOrganisationCmd(entry.get("Organization name"),
            entry.get("idRefog"),
            null);
    }

    @Given("All The organization doesn't exist")
    public void all_the_organization_doesn_t_exist() {
        organization = null;
    }

    @When("Administrator want to create a new organization Tree based on organization with idRefog {string} and with name {string}")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name(String idRefog, String organizationName) {
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(null);

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(organizationName, idRefog, new ArrayList<>());
        organization = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
    }

    @When("Administrator want to create a new organization Tree based on organization with idRefog {string} and  with name {string} and the following children")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name_and_the_following_children(String idRefog, String organizationName, List<ReferenceOrganisationCmd> childrenCmd) {
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(any(String.class)))
            .thenReturn(null);

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(organizationName, idRefog, childrenCmd);
        organization = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
    }

    @Then("a new organization tree is created with base {string} with a total of {string} Childs and {string} Organizations")
    public void a_new_organization_tree_is_created_with_base_with_a_total_of_childs_and_organizations(String nameOfBase, String numberOfChildren, String numberOfOrganization) {
        Mockito
            .verify(repositoryOfOrganization, times(1))
            .storeOrganization(any(Organization.class));

        assertThat(organization).isNotNull();
        assertThat(organization.getName()).isEqualTo(nameOfBase);
        assertThat(organization.numberOfChild()).isEqualTo(Integer.parseInt(numberOfChildren));
        assertThat(organization.numberOfOrganizationForThisTree()).isEqualTo(Integer.parseInt(numberOfOrganization));
    }

    @Given("The organization with idRefog {string} and with name {string} exist")
    public void the_organization_with_id_refog_and_with_name_exist(String idRefog, String name) {
        this.organization = new Organization(idRefog, name);
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(this.organization);
    }

    @When("Administrator want change the name of the  organization with idRefog {string}  with {string}")
    public void administrator_want_change_the_name_of_the_organization_with_IdRefog_with(String idRefog, String newName) {
        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(newName, idRefog, new ArrayList<>());
        organization = referenceOrganizationUseCase.updateOrganization(referenceOrganisationCmd);
    }

    @Then("tne name of the organization {string} is {string}")
    public void tne_name_of_the_organization_is(String idRefog, String newName) {
        assertThat(organization.getIdRefog()).isEqualTo(idRefog);
        assertThat(organization.getName()).isEqualTo(newName);
        Mockito
            .verify(repositoryOfOrganization, times(1))
            .storeOrganization(organization);

    }


}
