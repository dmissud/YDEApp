package org.yde.ydeapp.interfacerefi;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.nio.charset.Charset;

public class ApplicationSourceLight {

    @CsvBindByPosition(position = 0)
    private String identifiant;
    @CsvBindByPosition(position = 1)
    private String codeApp;
    @CsvBindByPosition(position = 2)
    private String shortDescription;
    @CsvBindByPosition(position = 3)
    private String longDescription;
    @CsvBindByPosition(position = 4)
    private String idResponsableMOE;
    @CsvBindByPosition(position = 5)
    private String firstNameResponsableMoe;
    @CsvBindByPosition(position = 6)
    private String lastNameResponsableMoe;

    public ApplicationSourceLight(String identifiant, String codeApp, String shortDescription, String longDescription, String idResponsableMOE, String firstNameResponsableMoe, String lastNameResponsableMoe) {
        this.identifiant = identifiant;
        this.codeApp = codeApp;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.idResponsableMOE = idResponsableMOE;
        this.firstNameResponsableMoe = firstNameResponsableMoe;
        this.lastNameResponsableMoe = lastNameResponsableMoe;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getCodeApp() {
        return codeApp;
    }

    public void setCodeApp(String codeApp) {
        this.codeApp = codeApp;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getIdResponsableMOE() {
        return idResponsableMOE;
    }

    public void setIdResponsableMOE(String idResponsableMOE) {
        this.idResponsableMOE = idResponsableMOE;
    }

    public String getFirstNameResponsableMoe() {
        return firstNameResponsableMoe;
    }

    public void setFirstNameResponsableMoe(String firstNameResponsableMoe) {
        this.firstNameResponsableMoe = firstNameResponsableMoe;
    }

    public String getLastNameResponsableMoe() {
        return lastNameResponsableMoe;
    }

    public void setLastNameResponsableMoe(String lastNameResponsableMoe) {
        this.lastNameResponsableMoe = lastNameResponsableMoe;
    }
}
