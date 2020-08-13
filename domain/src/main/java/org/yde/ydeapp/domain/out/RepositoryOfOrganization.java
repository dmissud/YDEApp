package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.Organization;

public interface RepositoryOfOrganization {
    Organization retrieveByName(String organizationName);

    void referenceOrganization(Organization root);
}
