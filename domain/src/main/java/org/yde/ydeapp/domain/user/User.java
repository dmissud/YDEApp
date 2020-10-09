package org.yde.ydeapp.domain.user;


import java.util.ArrayList;
import java.util.List;

public class User {

    private String uid;
    private String password;
    private int roles;


    public User(String uid, String password, List<RoleTypeEnum> roles) {
        this.uid = uid;
        this.password = password;
        this.roles = convertRoleAsListToRoleAsInteger(roles);
    }

    public User(String uid, String password, int roles) {
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

    public int getRoles() {
        return this.roles;
    }

    public List<RoleTypeEnum> getRolesAsList() {
        return convertRolesAsIntegerToRolesAsList(roles);
    }

    public void setRoles(List<RoleTypeEnum> roles) {
        this.roles = convertRoleAsListToRoleAsInteger(roles);
    }

    public static int convertRoleAsListToRoleAsInteger(List<RoleTypeEnum> roles) {
        return roles.stream()
            .map(role -> 2 << role.ordinal())
            .reduce(Integer::sum)
            .orElse(0);
    }

    public static List<RoleTypeEnum> convertRolesAsIntegerToRolesAsList(int rolesValues) {
        int valeur = RoleTypeEnum.values().length;
        List<RoleTypeEnum> roles = new ArrayList<>();

        while ((rolesValues > 0) && (valeur >= 0)) {
            int mask = (rolesValues & (2 << valeur));
            if (mask != 0) {
                roles.add(RoleTypeEnum.values()[valeur]);
                rolesValues = rolesValues - mask;
            }
            valeur--;
        }
        return roles;
    }
}
