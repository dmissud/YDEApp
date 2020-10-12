package org.yde.ydeapp.application.in.user;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.user.RoleTypeEnum;
import org.yde.ydeapp.domain.user.User;

import javax.validation.constraints.Size;
import java.util.List;

public interface ReferenceUserUseCase {

    User referenceNewUser(ReferenceUserCmd referenceUserCmd);

    User updateExistingUser(ReferenceUserCmd referenceUserCmd, String uid);

    User deleteUserByUid(String uid);

    @Validated
    class ReferenceUserCmd extends SelfValidating<ReferenceUserCmd> {

        // Todo regex
        @Size(min = 5, max = 7)
        private final String uid;

        private final String firstName;
        private final String lastName;
        private final String password;

        private final List<RoleTypeEnum> roles;

        public ReferenceUserCmd(String uid, String firstName, String lastName, String password, List<RoleTypeEnum> roles) {
            this.uid = uid;
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            this.roles = roles;
        }

        public String getUid() {
            return uid;
        }

        public String getPassword() {
            return password;
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

