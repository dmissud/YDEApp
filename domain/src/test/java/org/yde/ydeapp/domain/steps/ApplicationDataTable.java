package org.yde.ydeapp.domain.steps;

public class ApplicationDataTable {
    private final String codeApplication;
    private final String shortDescription;
    private final String longDescription;
    private final String nameOfResponsable;

    public ApplicationDataTable(String codeApplication, String shortDescription, String longDescription, String nameOfResponsable) {
        this.codeApplication = codeApplication;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.nameOfResponsable = nameOfResponsable;
    }

    public String getCodeApplication() { return codeApplication; }

    public String getShortDescription() { return shortDescription; }

    public String getLongDescription() { return longDescription; }

    public String getNameOfResponsable() { return nameOfResponsable; }
}
