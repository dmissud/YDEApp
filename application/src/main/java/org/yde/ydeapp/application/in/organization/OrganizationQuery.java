package org.yde.ydeapp.application.in.organization;

import org.yde.ydeapp.domain.organization.Organization;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.util.Arrays;
import java.util.List;

public interface OrganizationQuery {
    Organization getOrganizationTree(String idRefog);
    List<Organization> getOrganizationRoot();

    List<OrganizationIdent> getOrganizations();
}
