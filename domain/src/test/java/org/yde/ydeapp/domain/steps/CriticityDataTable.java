package org.yde.ydeapp.domain.steps;

public class CriticityDataTable {
    private final String privilegeInformation;
    private final String personalData;
    private final String serviceClass;
    private final String aviability;
    private final String rpo;
    private final String rto;

    public CriticityDataTable(String privilegeInformation, String personalData, String serviceClass, String aviability, String rpo, String rto) {
        this.privilegeInformation = privilegeInformation;
        this.personalData = personalData;
        this.serviceClass = serviceClass;
        this.aviability = aviability;
        this.rpo = rpo;
        this.rto = rto;
    }

    public String getPrivilegeInformation() {
        return privilegeInformation;
    }

    public String getPersonalData() {
        return personalData;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public String getAviability() {
        return aviability;
    }

    public String getRpo() {
        return rpo;
    }

    public String getRto() {
        return rto;
    }
}
