package org.yde.ydeapp.exposition.organization;

public class OrganizationDTO {
    String name;
    String idRefog;
    int numberOfOrganizations;
    int numberOfApplications;

    public OrganizationDTO() {
    }

    public OrganizationDTO(String idRefog, String name, int numberOfOrganizations, int numberOfApplications) {
        this.name = name;
        this.idRefog = idRefog;
        this.numberOfOrganizations = numberOfOrganizations;
        this.numberOfApplications = numberOfApplications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdRefog() {
        return idRefog;
    }

    public void setIdRefog(String idRefog) {
        this.idRefog = idRefog;
    }

    public int getNumberOfOrganizations() {
        return numberOfOrganizations;
    }

    public void setNumberOfOrganizations(int numberOfOrganizations) {
        this.numberOfOrganizations = numberOfOrganizations;
    }

    public int getNumberOfApplications() {
        return numberOfApplications;
    }

    public void setNumberOfApplications(int numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
    }
}
