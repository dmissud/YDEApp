package org.yde.ydeapp.infrastructure.application;

import javax.persistence.Embeddable;

@Embeddable
public class CriticityEntity {

    private String privilegeInformation;
    private String personalData;
    private String serviceClass;
    private String aviability;
    private String rpo;
    private String rto;

    public String getPrivilegeInformation() {
        return privilegeInformation;
    }

    public void setPrivilegeInformation(String privilegeInformation) {
        this.privilegeInformation = privilegeInformation;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getAviability() {
        return aviability;
    }

    public void setAviability(String aviability) {
        this.aviability = aviability;
    }

    public String getRpo() {
        return rpo;
    }

    public void setRpo(String rpo) {
        this.rpo = rpo;
    }

    public String getRto() {
        return rto;
    }

    public void setRto(String rto) {
        this.rto = rto;
    }
}
