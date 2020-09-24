package org.yde.ydeapp.domain.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.organization.Organization;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrganizationSteps {

    private Organization organization;

    @DataTableType
    public OrganizationDataTable organizationDataTableEntry(Map<String, String> entry) {
        return new OrganizationDataTable(entry.get("idRefog"), entry.get("Organization name"));
    }


    @Given("The organization doesn't exist")
    public void the_organization_doesn_t_exist() {
        this.organization = null;
    }

    @When("Administrator want to create a new organization with idRefog {string} and with name {string}")
    public void administrator_want_to_create_a_new_organization_with_name(String idRefog, String nameOrganization) {
        this.organization = new Organization(idRefog, nameOrganization);
    }

    @When("with the list of organisation in childs")
    public void with_the_list_of_organisation_in_childs(List<OrganizationDataTable> organizationChilds) {
        for (OrganizationDataTable organizationChild : organizationChilds) {
            this.organization.addChild(new Organization(organizationChild.idRefog, organizationChild.name));
        }
    }

    @Then("a new organization tree is created")
    public void a_new_organization_tree_is_created() {
        assertThat(this.organization.numberOfChild()).isEqualTo(4);
    }

    private class OrganizationDataTable {
        private String name;
        private String idRefog;

        public OrganizationDataTable(String idRefog, String name) {
            this.name = name;
            this.idRefog = idRefog;
        }
    }
}
