package org.yde.ydeapp.domain.steps;

public class ApplicationDataTable {
    private final String codeApplication;
    private final String shortDescription;
    private final String longDescription;
    private final String uid;
    private final String fisrtName;
    private final String lastName;

    public ApplicationDataTable(String codeApplication, String shortDescription, String longDescription, String uid,String fisrtName,String lastName) {
        this.codeApplication = codeApplication;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.uid = uid;
        this.fisrtName= fisrtName;
        this.lastName=lastName;

    }

    public String getCodeApplication() { return codeApplication; }

    public String getShortDescription() { return shortDescription; }

    public String getLongDescription() { return longDescription; }

    public String getUid() {
        return uid;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public String getLastName() {
        return lastName;
    }
}
