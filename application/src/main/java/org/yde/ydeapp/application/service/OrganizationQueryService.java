package org.yde.ydeapp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.OrganizationQuery;
import org.yde.ydeapp.domain.Organization;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

@Service
@Transactional
public class OrganizationQueryService implements OrganizationQuery {

    @Autowired
    RepositoryOfOrganization repositoryOfOrganization;

    @Override
    public Organization getOrganizationTree(String idRefog) {
        Organization organization = repositoryOfOrganization.retrieveByIdRefog(idRefog);
        if (organization == null) {
            throw new EntityNotFound(String.format("The organization with IdRefog %s doesn't exist in the repository", idRefog));
        }
        return organization;
    }
}