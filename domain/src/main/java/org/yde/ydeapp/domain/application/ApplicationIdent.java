package org.yde.ydeapp.domain.application;

public class ApplicationIdent {
    private String codeApplication;
    private String shortDescription;

    public ApplicationIdent(String codeApplication, String shortDescription) {
        this.codeApplication = codeApplication;
        this.shortDescription = shortDescription;
    }

    public String getCodeApplication() { return codeApplication; }

    public void setCodeApplication(String codeApplication) {
        this.codeApplication = codeApplication;
    }

    public String getShortDescription() { return shortDescription; }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
