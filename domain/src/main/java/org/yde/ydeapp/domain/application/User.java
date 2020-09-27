package org.yde.ydeapp.domain.application;


import java.util.HashSet;
import java.util.Set;

public class User {

    private String uid;
    private String password;
    private Set<RoleTypeEnum> roles = new HashSet<>();


    public User(String uid, String password, Set<RoleTypeEnum> roles) {
        this.uid = uid;
        this.password = password;
        this.roles = roles;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleTypeEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleTypeEnum> roles) {
        this.roles = roles;
    }
}
