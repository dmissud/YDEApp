package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.organization.ReferenceOrganizationUseCase;
import org.yde.ydeapp.domain.organization.Organization;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityIncorrect;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganizationManagementService implements ReferenceOrganizationUseCase {
    private final Logger log = LoggerFactory.getLogger(OrganizationManagementService.class);


    @Autowired
    RepositoryOfOrganization repositoryOfOrganization;

    @Override
    public Organization referenceOrganization(ReferenceOrganisationCmd referenceOrganisationCmd) {
        Organization rootOrganization = buildOrganization(referenceOrganisationCmd);
        rootOrganization.doItRoot();
        repositoryOfOrganization.storeOrganization(rootOrganization);
        log.debug("Reference of the organization {}", rootOrganization.getName());

        return rootOrganization;
    }

    @Override
    public Organization updateOrganization(ReferenceOrganisationCmd referenceOrganisationCmd, String idRefog) {
        if (idRefog.equals(referenceOrganisationCmd.getIdRefog())) {
            Organization organization = repositoryOfOrganization.retrieveByIdRefog(referenceOrganisationCmd.getIdRefog());
            if (organization == null) {
                throw new EntityNotFound(String.format("The organization %s Not found", referenceOrganisationCmd.getOrganizationName()));
            }

            organization = execUpdateOrganization(organization, referenceOrganisationCmd);

            repositoryOfOrganization.storeOrganization(organization);
            log.debug("Update of the organization {}", organization.getName());

            return organization;
        } else {
            throw new EntityIncorrect(String.format("Ident differ from key %s vs cmd %s", idRefog, referenceOrganisationCmd.getIdRefog()));
        }
    }

    private Organization execUpdateOrganization(Organization organization, ReferenceOrganisationCmd referenceOrganisationCmd) {
        referenceOrganisationCmd.validate();
        organization.setName(referenceOrganisationCmd.getOrganizationName());
        // Parcourir les organizations fille de la cmd et mettre à jour si elles sont présente sinon les builds
        List<Organization> actualChildren = organization.getChildren();
        for (ReferenceOrganisationCmd child : referenceOrganisationCmd.getChildren()) {
            List<Organization> isAChild = actualChildren.stream()
                .filter(organizationChild -> organizationChild.getIdRefog().equals(child.getIdRefog()))
                .collect(Collectors.toList());
            if (isAChild.isEmpty()) {
                organization.addChild(this.buildOrganization(child));
            } else {
                Organization updateOrganization = isAChild.get(0);
                actualChildren.remove(updateOrganization);
                organization.addChild(this.execUpdateOrganization(updateOrganization, child));
            }
        }


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
