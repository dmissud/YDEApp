package org.yde.ydeapp.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Organization {
    private final String idRefog;
    private String name;
    private List<Organization> children;

    public Organization(String idRefog, String name) {
        this.idRefog = idRefog;
        this.name = name;
        children = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public List<Organization> getChildren() {
        return Collections.unmodifiableList(children);
    }
}
