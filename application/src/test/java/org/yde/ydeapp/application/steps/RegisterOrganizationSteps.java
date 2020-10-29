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
import org.yde.ydeapp.application.in.organization.OrganizationQuery;
import org.yde.ydeapp.application.in.organization.ReferenceOrganizationUseCase;
import org.yde.ydeapp.application.in.organization.ReferenceOrganizationUseCase.ReferenceOrganisationCmd;
import org.yde.ydeapp.application.service.OrganizationManagementService;
import org.yde.ydeapp.application.service.OrganizationQueryService;
import org.yde.ydeapp.domain.organization.Organization;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

public class RegisterOrganizationSteps {
    @InjectMocks
    private final ReferenceOrganizationUseCase referenceOrganizationUseCase = new OrganizationManagementService();

    @InjectMocks
    private final OrganizationQuery organizationQuery = new OrganizationQueryService();

    @Mock
    private RepositoryOfOrganization repositoryOfOrganization;

    private Organization organizationThen;

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
//        organizationThen = null;
    }

    @When("Administrator want to create a new organization Tree based on organization with idRefog {string} and with name {string}")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name(String idRefog, String organizationName) {
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(null);

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(organizationName, idRefog, new ArrayList<>());
        organizationThen = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
    }

    @When("Administrator want to create a new organization Tree based on organization with idRefog {string} and  with name {string} and the following children")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name_and_the_following_children(String idRefog, String organizationName, List<ReferenceOrganisationCmd> childrenCmd) {
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(any(String.class)))
            .thenReturn(null);

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(organizationName, idRefog, childrenCmd);
        organizationThen = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
    }

    @Given("The organization with idRefog {string} and with name {string} exist")
    public void the_organization_with_id_refog_and_with_name_exist(String idRefog, String name) {
        Organization organizationGiven = new Organization(idRefog, name);
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(organizationGiven);
    }

    @When("Administrator want change the name of the  organization with idRefog {string}  with {string}")
    public void administrator_want_change_the_name_of_the_organization_with_IdRefog_with(String idRefog, String newName) {
        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(newName, idRefog, new ArrayList<>());
        organizationThen = referenceOrganizationUseCase.updateOrganization(referenceOrganisationCmd, idRefog);
    }

    @Then("the name of the organization {string} is {string}")
    public void tne_name_of_the_organization_is(String idRefog, String newName) {
        assertThat(organizationThen.getIdRefog()).isEqualTo(idRefog);
        assertThat(organizationThen.getName()).isEqualTo(newName);
        Mockito
            .verify(repositoryOfOrganization, times(1))
            .storeOrganization(organizationThen);
    }

    @Given("The organization with idRefog {string} and with name {string} exist in the repository")
    public void the_organization_with_id_refog_and_with_name_exist_in_the_repository(String idRefog, String nameOfOrganization) {
        Organization organizationGiven = new Organization(idRefog, nameOfOrganization);
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(organizationGiven);
    }

    @When("Administrator want to consult a organization Tree based on organization with idRefog {string}")
    public void administrator_want_to_consult_a_organization_tree_based_on_organization_with_id_refog_and_with_name(String idRefog) {
        organizationThen = organizationQuery.getOrganizationTree(idRefog);
    }

    @Given("The Organization with idRefog {string} and  with name {string} exist and have the following children")
    public void the_organization_with_id_refog_and_with_name_exist_and_have_the_following_children(String idRefog, String organizationName, List<ReferenceOrganisationCmd> childrenCmd) {
        Organization organizationInRepository = new Organization(idRefog, organizationName);
        childrenCmd.forEach(cmd -> organizationInRepository.addChild(new Organization(cmd.getIdRefog(), cmd.getOrganizationName())));
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(organizationInRepository);
    }

    @When("Administrator want to update the organization Tree based on organization with idRefog {string} and  with name {string} and the following children")
    public void administrator_want_to_update_the_organization_tree_based_on_organization_with_id_refog_and_with_name_and_the_following_children(String idRefog, String name, List<ReferenceOrganisationCmd> childrenCmd) {
        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(name, idRefog, childrenCmd);
        organizationThen = referenceOrganizationUseCase.updateOrganization(referenceOrganisationCmd, idRefog);
    }

    @Then("a organization tree exist with base {string} with a total of {string} Childs and {string} Organizations")
    public void a_organization_tree_exist_with_base_with_a_total_of_childs_and_organizations(String nameOfBase, String numberOfChildren, String numberOfOrganization) {
        Mockito
            .verify(repositoryOfOrganization, atLeastOnce())
            .retrieveByIdRefog(any(String.class));

        assertThat(organizationThen).isNotNull();
        assertThat(organizationThen.getName()).isEqualTo(nameOfBase);
        assertThat(organizationThen.numberOfChild()).isEqualTo(Integer.parseInt(numberOfChildren));
        assertThat(organizationThen.numberOfOrganizationForThisTree()).isEqualTo(Integer.parseInt(numberOfOrganization));
    }
}
