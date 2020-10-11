package org.yde.ydeapp.domain.organization;

import org.yde.ydeapp.domain.application.ApplicationIdent;

import java.util.*;

public class Organization {
    private final String idRefog;
    private String name;
    private List<Organization> children;
    private Map<String, ApplicationIdent> applications;
    private boolean root;

    public Organization(String idRefog, String name) {
        this.idRefog = idRefog;
        this.name = name;
        this.children = new ArrayList<>();
        this.applications = new HashMap<>();
        this.root = false;
    }

    public void doItRoot() {
        root = true;
    }

    public boolean isRoot() {
        return root;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdRefog() { return idRefog; }


    public void addChild(Organization organization) {
        children.add(organization);
    }

    public int numberOfChild() {
        return children.size();
    }

    public int numberOfOrganizationForThisTree() {
        int totalOfOrganization = 1;
        for(Organization organization: children) {
            totalOfOrganization = totalOfOrganization + organization.numberOfOrganizationForThisTree();
        }
        return totalOfOrganization;
    }

    public int numberOfApplicationForThisTree() {
        int totalOfApplications = applications.size();
        for(Organization organization: children) {
            totalOfApplications = totalOfApplications + organization.numberOfApplicationForThisTree();
        }
        return totalOfApplications;
    }

    public String getName() {
        return name;
    }

    public List<Organization> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addApplication(ApplicationIdent applicationIdent) {
        if (applications.containsKey(applicationIdent.getCodeApplication())) {
            applications.replace(applicationIdent.getCodeApplication(), applicationIdent);
        } else {
            applications.put(applicationIdent.getCodeApplication(), applicationIdent);
        }
    }

    public Collection<ApplicationIdent> getApplications() {
        return applications.values();
    }
}
