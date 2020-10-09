package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.organization.ReferenceOrganizationUseCase;
import org.yde.ydeapp.domain.organization.Organization;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

@Service
@Transactional
public class OrganizationManagementService implements ReferenceOrganizationUseCase {
    private final Logger log = LoggerFactory.getLogger(OrganizationManagementService.class);


    @Autowired
    RepositoryOfOrganization repositoryOfOrganization;

    @Override
    public Organization referenceOrganization(ReferenceOrganisationCmd referenceOrganisationCmd) {
        Organization rootOrganization = buildOrganization(referenceOrganisationCmd);

        repositoryOfOrganization.storeOrganization(rootOrganization);
        log.debug("Reference of the organization {}", rootOrganization.getName());

        return rootOrganization;
    }

    @Override
    public Organization updateOrganization(ReferenceOrganisationCmd referenceOrganisationCmd) {
        referenceOrganisationCmd.validate();
        Organization organization = repositoryOfOrganization.retrieveByIdRefog(referenceOrganisationCmd.getIdRefog());
        if (organization == null) {
            throw new EntityNotFound(String.format("The organization %s Not found", referenceOrganisationCmd.getOrganizationName()));
        }
        organization.setName(referenceOrganisationCmd.getOrganizationName());

        repositoryOfOrganization.storeOrganization(organization);
        log.debug("Update of the organization {}", organization.getName());

        return organization;
    }

    private Organization buildOrganization(ReferenceOrganisationCmd referenceOrganisationCmd) {
        referenceOrganisationCmd.validate();
        Organization organization = repositoryOfOrganization.retrieveByIdRefog(referenceOrganisationCmd.getIdRefog());
        if (organization != null) {
            throw new EntityAlreadyExist(String.format("The organization %s all ready exist", referenceOrganisationCmd.getOrganizationName()));
        } else {
            organization = new Organization(referenceOrganisationCmd.getIdRefog(), referenceOrganisationCmd.getOrganizationName());
            log.debug("Build of the organization {}", organization.getName());
            if (referenceOrganisationCmd.getChildren() != null) {
                for (ReferenceOrganisationCmd referenceOrganisationCmdChild : referenceOrganisationCmd.getChildren()) {
                    organization.addChild(buildOrganization(referenceOrganisationCmdChild));
                }
            }
        }
        return organization;
    }
}
