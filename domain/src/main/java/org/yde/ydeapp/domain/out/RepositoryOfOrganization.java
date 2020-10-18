package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.organization.Organization;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.util.List;

public interface RepositoryOfOrganization {
    Organization retrieveByIdRefog(String organizationIdRefog);
    OrganizationIdent retriveIdentByIdRefog(String idRefog);

    void storeOrganization(Organization organization);

    List<Organization> retrieveRootOrganizations();

    List<OrganizationIdent> retrieveOrganizations();
}
