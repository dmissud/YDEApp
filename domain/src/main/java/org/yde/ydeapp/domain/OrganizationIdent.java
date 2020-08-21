package org.yde.ydeapp.domain;

public class OrganizationIdent {
    private final String idRefog;
    private final String name;


    public OrganizationIdent(String idRefog, String name) {
        this.idRefog = idRefog;
        this.name = name;
    }

    public String getIdRefog() {
        return idRefog;
    }

    public String getName() {
        return name;
    }
}
