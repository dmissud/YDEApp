package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.Organization;

public interface RepositoryOfOrganization {
    Organization findByName(String organizationName);

    void referenceOrganization(Organization root);
}
