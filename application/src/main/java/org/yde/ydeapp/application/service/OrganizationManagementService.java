package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase;
import org.yde.ydeapp.domain.Organization;
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
        referenceOrganisationCmd.validate();
        Organization root = buildOrganization(referenceOrganisationCmd);
        repositoryOfOrganization.referenceOrganization(root);
        log.debug("Reference of the organization {}", root.getName());
        return root;
    }

    private Organization buildOrganization(ReferenceOrganisationCmd referenceOrganisationCmd) {
        referenceOrganisationCmd.validate();
        Organization organization;
        try {
            repositoryOfOrganization.retrieveByIdRefog(referenceOrganisationCmd.getOrganizationName());
            throw new EntityAlreadyExist(String.format("The organization %s all ready exist", referenceOrganisationCmd.getOrganizationName()));
        } catch (EntityNotFound ex) {
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
