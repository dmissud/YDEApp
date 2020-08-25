package org.yde.ydeapp.domain.steps;

import java.util.Date;

public class ApplicationDataTable {
    private final String codeApplication;
    private final String shortDescription;
    private final String longDescription;
    private final String uid;
    private final String fisrtName;
    private final String lastName;
    private final String idRefogOrganization;
    private final String state;
    private final Date dateOfCreation;
    private final Date dateOfLastUpdate;
    private final Date dateEndInReality;

    public ApplicationDataTable(String codeApplication, String shortDescription,
                                String longDescription, String uid,
                                String fisrtName, String lastName,
                                String idRefogOrganization, String state,
                                Date dateOfCreation, Date dateOfLastUpdate,
                                Date dateEndInReality) {
        this.codeApplication = codeApplication;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.uid = uid;
        this.fisrtName = fisrtName;
        this.lastName = lastName;
        this.idRefogOrganization = idRefogOrganization;
        this.state = state;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastUpdate = dateOfLastUpdate;
        this.dateEndInReality = dateEndInReality;
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

    public String getUid() {
        return uid;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getState() {
        return state;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Date getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public Date getDateEndInReality() {
        return dateEndInReality;
    }
}
