package org.yde.ydeapp.infrastructure.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.application.ApplicationIdent;
import org.yde.ydeapp.domain.organization.Organization;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;
import org.yde.ydeapp.infrastructure.application.ApplicationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void storeOrganization(Organization organization) {
        repositoryOfOrganizationJpa.save(mapDomainToEntity(organization));
    }

    @Override
    public List<Organization> retrieveRootOrganizations() {
        return repositoryOfOrganizationJpa.findAllByRootIs(true).stream().map(this::mapEntityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Organization> retrieveOrganizations() {
        return repositoryOfOrganizationJpa.findAll().stream().map(this::mapEntityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationIdent> retrieveOrganizationsIdent() {
        return repositoryOfOrganizationJpa.findAllOrganizationIdent();
    }

    @Override
    public OrganizationIdent retriveIdentByIdRefog(String idRefog) {
        return repositoryOfOrganizationJpa.retrieveOrganizationIdent(idRefog);
    }

    private Organization mapEntityToDomain(OrganizationEntity organizationEntity) {
        Organization organization = new Organization(organizationEntity.getIdRefog(), organizationEntity.getName());
        if (organizationEntity.isRoot()) {
            organization.doItRoot();
        }
        for(ApplicationEntity applicationEntity : organizationEntity.getApplications()) {
            ApplicationIdent applicationIdent = new ApplicationIdent(applicationEntity.getCodeApp(), applicationEntity.getShortDescription());
            organization.addApplication(applicationIdent);
        }
        for (OrganizationEntity organisationEntityChild : organizationEntity.getChildren()) {
            organization.addChild(mapEntityToDomain(organisationEntityChild));
        }
        return organization;
    }

    private OrganizationEntity mapDomainToEntity(Organization organization) {
        OrganizationEntity organizationEntity = repositoryOfOrganizationJpa.findByIdRefog(organization.getIdRefog());

        if (organizationEntity == null) {
            organizationEntity = new OrganizationEntity();
            organizationEntity.setIdRefog(organization.getIdRefog());
        }
        organizationEntity.setName(organization.getName());
        organizationEntity.setRoot(organization.isRoot());
        List<OrganizationEntity> children = new ArrayList<>();

        for (Organization organizationChild : organization.getChildren()) {
            children.add(mapDomainToEntity(organizationChild));
        }
        organizationEntity.setChildren(children);

        return organizationEntity;
    }
}
