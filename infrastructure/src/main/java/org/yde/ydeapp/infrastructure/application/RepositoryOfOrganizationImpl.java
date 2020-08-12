package org.yde.ydeapp.infrastructure.application;

import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.Organization;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

@Repository
public class RepositoryOfOrganizationImpl implements RepositoryOfOrganization {
    @Override
    public Organization findByName(String organizationName) {
        return null;
    }

    @Override
    public void referenceOrganization(Organization root) {

    }
}
