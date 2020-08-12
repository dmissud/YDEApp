package org.yde.ydeapp.domain;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String name;
    private Organization parent;
    private List<Organization> childs;

    public Organization(String name) {
        this.name = name;
        parent = null;
        childs = new ArrayList<>();
    }

    public void addChild(Organization organization) {
        childs.add(organization);
        organization.haveForParent(this);
    }

    public void haveForParent(Organization organization) {
        this.parent = organization;
    }

    public int numberOfChild() {
        return childs.size();
    }

    public int numberOfOrganizationForThisTree() {
        int totalOfOrganization = 1;
        for(Organization organization:childs) {
            totalOfOrganization = totalOfOrganization + organization.numberOfOrganizationForThisTree();
        }
        return totalOfOrganization;
    }

    public String getName() {
        return name;
    }
}
