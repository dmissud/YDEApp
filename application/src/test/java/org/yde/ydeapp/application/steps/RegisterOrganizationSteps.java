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
import static org.mockito.Mockito.times;

public class RegisterOrganizationSteps {
    @InjectMocks
    private final ReferenceOrganizationUseCase referenceOrganizationUseCase = new OrganizationManagementService();

    @InjectMocks
    private final OrganizationQuery organizationQuery = new OrganizationQueryService();

    @Mock
    private RepositoryOfOrganization repositoryOfOrganization;

    private Organization organizationInRepository;
    private Organization organizationSearch;

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
        organizationInRepository = null;
    }

    @When("Administrator want to create a new organization Tree based on organization with idRefog {string} and with name {string}")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name(String idRefog, String organizationName) {
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(null);

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(organizationName, idRefog, new ArrayList<>());
        organizationInRepository = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
    }

    @When("Administrator want to create a new organization Tree based on organization with idRefog {string} and  with name {string} and the following children")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name_and_the_following_children(String idRefog, String organizationName, List<ReferenceOrganisationCmd> childrenCmd) {
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(any(String.class)))
            .thenReturn(null);

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(organizationName, idRefog, childrenCmd);
        organizationInRepository = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
    }

    @Then("a new organization tree is created with base {string} with a total of {string} Childs and {string} Organizations")
    public void a_new_organization_tree_is_created_with_base_with_a_total_of_childs_and_organizations(String nameOfBase, String numberOfChildren, String numberOfOrganization) {
        Mockito
            .verify(repositoryOfOrganization, times(1))
            .storeOrganization(any(Organization.class));

        assertThat(organizationInRepository).isNotNull();
        assertThat(organizationInRepository.getName()).isEqualTo(nameOfBase);
        assertThat(organizationInRepository.numberOfChild()).isEqualTo(Integer.parseInt(numberOfChildren));
        assertThat(organizationInRepository.numberOfOrganizationForThisTree()).isEqualTo(Integer.parseInt(numberOfOrganization));
    }

    @Given("The organization with idRefog {string} and with name {string} exist")
    public void the_organization_with_id_refog_and_with_name_exist(String idRefog, String name) {
        this.organizationInRepository = new Organization(idRefog, name);
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(this.organizationInRepository);
    }

    @When("Administrator want change the name of the  organization with idRefog {string}  with {string}")
    public void administrator_want_change_the_name_of_the_organization_with_IdRefog_with(String idRefog, String newName) {
        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(newName, idRefog, new ArrayList<>());
        organizationInRepository = referenceOrganizationUseCase.updateOrganization(referenceOrganisationCmd, idRefog);
    }

    @Then("tne name of the organization {string} is {string}")
    public void tne_name_of_the_organization_is(String idRefog, String newName) {
        assertThat(organizationInRepository.getIdRefog()).isEqualTo(idRefog);
        assertThat(organizationInRepository.getName()).isEqualTo(newName);
        Mockito
            .verify(repositoryOfOrganization, times(1))
            .storeOrganization(organizationInRepository);
    }

    @Given("The organization with idRefog {string} and with name {string} exist in the repository")
    public void the_organization_with_id_refog_and_with_name_exist_in_the_repository(String idRefog, String nameOfOrganization) {
        organizationSearch = null;
        organizationInRepository = new Organization(idRefog, nameOfOrganization);
        Mockito
            .when(repositoryOfOrganization.retrieveByIdRefog(idRefog))
            .thenReturn(organizationInRepository);
    }

    @When("Administrator want to consult a organization Tree based on organization with idRefog {string}")
    public void administrator_want_to_consult_a_organization_tree_based_on_organization_with_id_refog_and_with_name(String idRefog) {
        organizationSearch = organizationQuery.getOrganizationTree(idRefog);
    }

    @Then("a new organization tree exist with base {string} with a total of {string} Childs and {string} Organizations")
    public void a_new_organization_tree_exist_with_base_with_a_total_of_childs_and_organizations(String nameOfBase, String numberOfChildren, String numberOfOrganization) {
        Mockito
            .verify(repositoryOfOrganization, times(1))
            .retrieveByIdRefog(any(String.class));

        assertThat(organizationSearch).isNotNull();
        assertThat(organizationSearch.getName()).isEqualTo(nameOfBase);
        assertThat(organizationSearch.numberOfChild()).isEqualTo(Integer.parseInt(numberOfChildren));
        assertThat(organizationSearch.numberOfOrganizationForThisTree()).isEqualTo(Integer.parseInt(numberOfOrganization));
    }

}
