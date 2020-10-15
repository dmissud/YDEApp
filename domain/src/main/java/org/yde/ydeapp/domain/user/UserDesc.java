package org.yde.ydeapp.domain.user;

import java.util.List;

public class UserDesc {
    private final String firstName;
    private final String lastName;
    private final String uid;
    private final List<RoleTypeEnum> roles;

    public UserDesc(String firstName, String lastName, String uid, int roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
        this.roles = User.convertRolesAsIntegerToRolesAsList(roles);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUid() {
        return uid;
    }

    public List<RoleTypeEnum> getRoles() {
        return roles;
    }
}
