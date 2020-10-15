package org.yde.ydeapp.domain.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.yde.ydeapp.domain.user.RoleTypeEnum;
import org.yde.ydeapp.domain.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserSteps {

    private User user;


    @Given("a admin creates a new user")
    public void a_admin_creates_a_new_user() {
        this.user = null;
    }


    @When("admin reference new user with firstName {string} and with lastName {string} and with uid {string} and with password {string} and with roles {string}")
    public void admin_reference_new_user_with_first_name_and_with_last_name_and_with_uid_and_with_password_and_with_roles(String firstName, String lastName, String uid,
                                                                String password, String roles) {
        int rolesTest;
        if(roles.contains("4")) {
            rolesTest = 4;
        } else if(roles.contains("2")) {
            rolesTest = 2;
        } else if(roles.contains("6")) {
            rolesTest = 6;
        } else {
            rolesTest = 0;
        }

        this.user = new User(firstName, lastName, uid, password, rolesTest);
    }

    @Then("user reference succeeded")
    public void user_reference_succeeded() {
        assertThat(this.user != null);
        System.out.println("User : " + user.getLastName()+" créé avec les droits "+ user.getRolesAsList());

    }
}
