package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.Organization;

public interface RepositoryOfOrganization {
    Organization retrieveByIdRefog(String organizationIdRefog);
    void storeOrganization(Organization organization);
}
