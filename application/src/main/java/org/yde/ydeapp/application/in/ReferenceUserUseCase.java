package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.application.RoleTypeEnum;
import org.yde.ydeapp.domain.application.User;

import javax.validation.constraints.Size;
import java.util.Set;

public interface ReferenceUserUseCase {

    User referenceNewUser(ReferenceUserCmd referenceUserCmd);
    User updateExistingUser(ReferenceUserCmd referenceUserCmd, String uid);
    User deleteUserByUid(String uid);

    @Validated
    class ReferenceUserCmd extends SelfValidating<ReferenceUserCmd> {

        @Size(min=5 , max=7)
        private final String uid;

        private final String password;

        private final Set<RoleTypeEnum> roles;

        public ReferenceUserCmd(String uid, String password, Set<RoleTypeEnum> roles) {
            this.uid = uid;
            this.password = password;
            this.roles = roles;
        }

        public String getUid() {
            return uid;
        }

        public String getPassword() {
            return password;
        }

        public Set<RoleTypeEnum> getRoles() {
            return roles;
        }
    }

}
