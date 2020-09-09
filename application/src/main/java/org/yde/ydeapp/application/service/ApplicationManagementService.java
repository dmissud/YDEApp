package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.*;
import org.yde.ydeapp.domain.*;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.util.List;

@Service
@Transactional
public class ApplicationManagementService implements ReferenceApplicationUseCase, ApplicationQuery {

    private final Logger log = LoggerFactory.getLogger(ApplicationManagementService.class);

    @Autowired
    private RepositoryOfApplication repositoryOfApplication;

    @Autowired
    private RepositoryOfOrganization repositoryOforganization;

    @Override
    public StateCmdEnum referenceOrUpdateApplication(ReferenceApplicationCmd referenceApplicationCmd) {
        StateCmdEnum stateCmd;

        referenceApplicationCmd.validate();

        Personne personne = new Personne(referenceApplicationCmd.getUid(), referenceApplicationCmd.getFirstName(), referenceApplicationCmd.getLastName());

        CycleLife cycleLife = new CycleLife(referenceApplicationCmd.getState(),
                                            referenceApplicationCmd.getDateOfCreation(),
                                            referenceApplicationCmd.getDateOfCreation(),
                                            referenceApplicationCmd.getDateEndInReality());

        OrganizationIdent organizationIdent = repositoryOforganization.retriveIdentByIdRefog(referenceApplicationCmd.getIdRefOrganizationMoe());
        Application application = repositoryOfApplication.retrieveByAppCode(referenceApplicationCmd.getCodeApp());

        if (organizationIdent == null) {
            if (application == null) {
                stateCmd = StateCmdEnum.IGNORE;
                log.trace("Application {} ignored. {} not in", referenceApplicationCmd.getCodeApp(), referenceApplicationCmd.getIdRefOrganizationMoe());
            } else {
                stateCmd = StateCmdEnum.NO_MORE_UPDATED;
                log.trace("Application {} not updated. {} no more in", referenceApplicationCmd.getCodeApp(), referenceApplicationCmd.getIdRefOrganizationMoe());
            }
        } else {
            if (application != null) {
                log.trace("Application {} updated", application.getCodeApplication());
                application.updateLongDescription(referenceApplicationCmd.getLongDescription());
                application.updateShortDescription(referenceApplicationCmd.getShortDescription());
                application.updateResponsable(personne);
                application.updateOrganization(organizationIdent);
                application.updateCycleLife(cycleLife);
                repositoryOfApplication.updateApplication(application);
                stateCmd = StateCmdEnum.UPDATE;
            } else {
                application = new Application.Builder(referenceApplicationCmd.getCodeApp())
                    .withShortDescription(referenceApplicationCmd.getShortDescription())
                    .withLongDescription(referenceApplicationCmd.getLongDescription())
                    .withResponsable(personne)
                    .withOrganization(organizationIdent)
                    .withCycleLife(cycleLife)
                    .build();
                log.trace("Application {} created", application.getCodeApplication());
                repositoryOfApplication.referenceApplication(application);
                stateCmd = StateCmdEnum.REFERENCE;
            }
        }
        return stateCmd;
    }

    @Override
    public Application updateApplication(String codeApplication, ReferenceApplicationCmd referenceApplicationCmd) {
        referenceApplicationCmd.validate();
        OrganizationIdent organizationIdent = repositoryOforganization.retriveIdentByIdRefog(referenceApplicationCmd.getIdRefOrganizationMoe());
        if (organizationIdent == null) {
            throw new EntityNotFound(String.format("Organization %s is not in", referenceApplicationCmd.getIdRefOrganizationMoe()));
        }
        Application application = repositoryOfApplication.retrieveByAppCode(codeApplication);
        if (application == null) {
            throw new EntityNotFound(String.format("Application %s is not in", referenceApplicationCmd.getCodeApp()));
        }

        application.updateLongDescription(referenceApplicationCmd.getLongDescription());
        application.updateShortDescription(referenceApplicationCmd.getShortDescription());
        application.updateOrganization(organizationIdent);

        Personne personne = new Personne(referenceApplicationCmd.getUid(), referenceApplicationCmd.getFirstName(), referenceApplicationCmd.getLastName());
        application.updateResponsable(personne);

        CycleLife cycleLife = new CycleLife(referenceApplicationCmd.getState(),
                referenceApplicationCmd.getDateOfCreation(),
                referenceApplicationCmd.getDateOfCreation(),
                referenceApplicationCmd.getDateEndInReality());
        application.updateCycleLife(cycleLife);
        repositoryOfApplication.updateApplication(application);

        return application;
    }

    @Override
    public Application getApplication(String codeApplication) {
        Application application = repositoryOfApplication.retrieveByAppCode(codeApplication);
        if (application == null) {
            throw new EntityNotFound(String.format("Application with %s is not in the Repository", codeApplication));
        }
        return application;
    }

    @Override
    public List<ApplicationIdent> getAllApplicationsIdent() {
        return repositoryOfApplication.retrieveIdentOfAllApplications();
    }
}
