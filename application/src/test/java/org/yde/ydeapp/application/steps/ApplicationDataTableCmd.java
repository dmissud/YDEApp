package org.yde.ydeapp.application.steps;

public class ApplicationDataTableCmd {
    private final String codeApplication;
    private final String shortDescription;
    private final String longDescription;
    private final String idRefogOrganization;

    public ApplicationDataTableCmd(String codeApplication, String shortDescription,
                                   String longDescription,
                                   String idRefogOrganization) {
        this.codeApplication = codeApplication;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.idRefogOrganization = idRefogOrganization;
    }

    public String getIdRefogOrganization() {
        return idRefogOrganization;
    }

    public String getCodeApplication() {
        return codeApplication;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

}

