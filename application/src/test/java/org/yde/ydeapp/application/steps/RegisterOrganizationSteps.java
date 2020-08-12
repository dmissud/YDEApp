package org.yde.ydeapp.application.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoRule;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase.ReferenceOrganisationCmd;
import org.yde.ydeapp.application.service.OrganizationManagementService;
import org.yde.ydeapp.domain.Organization;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

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


    @Given("All The organization doesn't exist")
    public void all_the_organization_doesn_t_exist() {
        organization = null;
    }

    @When("Administrator want to create a new organization Tree based on organization with name {string}")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name(String organizationName) {
        Mockito
            .when(repositoryOfOrganization.findByName(organizationName))
            .thenThrow(new EntityNotFound(""));

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(organizationName, new ArrayList<>());
        organization = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
    }

    @When("Administrator want to create a new organization Tree based on organization with name {string} and the following children")
    public void administrator_want_to_create_a_new_organization_tree_based_on_organization_with_name_and_the_following_children(String string, io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }

    @Then("a new organization tree is created with base {string} with a total of {string} Childs and {string} Organizations")
    public void a_new_organization_tree_is_created_with_base_with_a_total_of_childs_and_organizations(String nameOfBase, String numberOfChildren, String numberOfOrganization) {
        assertThat(organization).isNotNull();
        assertThat(organization.getName()).isEqualTo(nameOfBase);
        assertThat(organization.numberOfChild()).isEqualTo(Integer.parseInt(numberOfChildren));
        assertThat(organization.numberOfOrganizationForThisTree()).isEqualTo(Integer.parseInt(numberOfOrganization));
    }


}
