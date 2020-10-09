package org.yde.ydeapp.domain.user;


import java.util.List;

public class User {

    private String uid;
    private String password;
    private List<RoleTypeEnum> roles;


    public User(String uid, String password, List<RoleTypeEnum> roles) {
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

    public List<RoleTypeEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleTypeEnum> roles) {
        this.roles = roles;
    }
}
