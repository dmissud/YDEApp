package org.yde.ydeapp.infrastructure.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.Organization;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryOfOrganizationImpl implements RepositoryOfOrganization {

    @Autowired
    RepositoryOfOrganizationJpa repositoryOfOrganizationJpa;

    @Override
    public Organization retrieveByIdRefog(String organizationIdRefog) {
        OrganizationEntity organizationEntity = repositoryOfOrganizationJpa.findByIdRefog(organizationIdRefog);

        if (organizationEntity != null) {
            return mapEntityToDomain(organizationEntity);
        } else {
            return null;
        }
    }

    private Organization mapEntityToDomain(OrganizationEntity organizationEntity) {
        Organization organization = new Organization(organizationEntity.getIdRefog(), organizationEntity.getName());
        for (OrganizationEntity organisationEntityChild : organizationEntity.getChildren()) {
            organization.addChild(mapEntityToDomain(organisationEntityChild));
        }
        return organization;
    }

    @Override
    public void storeOrganization(Organization organization) {
        repositoryOfOrganizationJpa.save(mapDomainToEntity(organization));
    }

    private OrganizationEntity mapDomainToEntity(Organization organization) {
        OrganizationEntity organizationEntity = repositoryOfOrganizationJpa.findByIdRefog(organization.getIdRefog());

        if (organizationEntity == null) {
            organizationEntity = new OrganizationEntity();
            organizationEntity.setName(organization.getName());
            organizationEntity.setIdRefog(organization.getIdRefog());
        }

        List<OrganizationEntity> children = new ArrayList<>();

        for (Organization organizationChild : organization.getChildren()) {
            children.add(mapDomainToEntity(organizationChild));
        }
        organizationEntity.setChildren(children);

        return organizationEntity;
    }
}
