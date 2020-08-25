package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.Organization;
import org.yde.ydeapp.domain.OrganizationIdent;

public interface RepositoryOfOrganization {
    Organization retrieveByIdRefog(String organizationIdRefog);
    OrganizationIdent retriveIdentByIdRefog(String idRefog);

    void storeOrganization(Organization organization);
}
