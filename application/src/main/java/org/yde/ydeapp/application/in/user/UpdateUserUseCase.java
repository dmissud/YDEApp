package org.yde.ydeapp.application.in.user;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.user.RoleTypeEnum;
import org.yde.ydeapp.domain.user.User;

import java.util.List;

public interface UpdateUserUseCase {

    User updateExistingUser(String uid, UpdateUserCmd updateUserCmd );
    @Validated
    class UpdateUserCmd extends SelfValidating<UpdateUserCmd> {
        private final String firstName;
        private final String lastName;

        private final List<RoleTypeEnum> roles;

        public UpdateUserCmd(String firstName, String lastName, List<RoleTypeEnum> roles) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.roles = roles;
        }

        public List<RoleTypeEnum> getRoles() {
            return roles;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

}

