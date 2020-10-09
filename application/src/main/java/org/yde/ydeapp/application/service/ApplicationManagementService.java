package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.application.ApplicationQuery;
import org.yde.ydeapp.application.in.application.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.application.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.application.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.domain.application.*;
import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.StateUpdateEnum;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;
import org.yde.ydeapp.domain.out.RepositoryOfFluxRefi;
import org.yde.ydeapp.domain.out.RepositoryOfOrganization;

import java.util.List;

@Service
@Transactional()
public class ApplicationManagementService implements ReferenceApplicationUseCase,
    ReferenceCollectionOfApplicationUseCase,
    ApplicationQuery {

    private final Logger log = LoggerFactory.getLogger(ApplicationManagementService.class);

    @Autowired
    private RepositoryOfApplication repositoryOfApplication;

    @Autowired
    private RepositoryOfOrganization repositoryOforganization;

    @Autowired
    RepositoryOfFluxRefi repositoryOfFluxRefi;

    @Override
    public StateUpdateEnum referenceOrUpdateApplication(ReferenceApplicationCmd referenceApplicationCmd) {
        StateUpdateEnum stateCmd;

        referenceApplicationCmd.validate();


        OrganizationIdent organizationIdent = repositoryOforganization.retriveIdentByIdRefog(referenceApplicationCmd.getIdRefOrganizationMoe());
        Application application;
        stateCmd = StateUpdateEnum.IGNORE;

        if (organizationIdent != null) {
            Personne personne = new Personne(referenceApplicationCmd.getUid(), referenceApplicationCmd.getFirstName(), referenceApplicationCmd.getLastName());

            CycleLife cycleLife = new CycleLife(referenceApplicationCmd.getState(),
                referenceApplicationCmd.getDateOfCreation(),
                referenceApplicationCmd.getDateOfCreation(),
                referenceApplicationCmd.getDateEndInReality());
            ItSolution itSolution = new ItSolution(referenceApplicationCmd.getTypeOfSolution(),
                referenceApplicationCmd.getNameOfFirmware(),
                referenceApplicationCmd.getLabelOfSourcingMode());
            Criticity criticity = new Criticity(referenceApplicationCmd.getPrivilegeInformation(),
                referenceApplicationCmd.getPersonalData(),
                referenceApplicationCmd.getServiceClass(),
                referenceApplicationCmd.getAvailability(),
                referenceApplicationCmd.getRpo(),
                referenceApplicationCmd.getRto());


            application = repositoryOfApplication.retrieveByAppCode(referenceApplicationCmd.getCodeApp());
            if (application != null) {
                log.trace("Application {} updated", application.getCodeApplication());
                application.updateLongDescription(referenceApplicationCmd.getLongDescription());
                application.updateShortDescription(referenceApplicationCmd.getShortDescription());
                application.updateResponsable(personne);
                application.updateOrganization(organizationIdent);
                application.updateCycleLife(cycleLife);
                application.updateItSolution(itSolution);
                application.updateCriticity(criticity);
                repositoryOfApplication.updateApplication(application);
                stateCmd = StateUpdateEnum.UPDATE;
            } else {
                application = new Application.Builder(referenceApplicationCmd.getCodeApp())
                    .withShortDescription(referenceApplicationCmd.getShortDescription())
                    .withLongDescription(referenceApplicationCmd.getLongDescription())
                    .withResponsable(personne)
                    .withOrganization(organizationIdent)
                    .withCycleLife(cycleLife)
                    .withItSolution(itSolution)
                    .withCriticity(criticity)
                    .build();
                log.trace("Application {} created", application.getCodeApplication());
                repositoryOfApplication.referenceApplication(application);
                stateCmd = StateUpdateEnum.REFERENCE;
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
        ItSolution itSolution = new ItSolution(referenceApplicationCmd.getTypeOfSolution(),
            referenceApplicationCmd.getNameOfFirmware(),
            referenceApplicationCmd.getLabelOfSourcingMode());
        application.updateItSolution(itSolution);

        Criticity criticity = new Criticity(referenceApplicationCmd.getPrivilegeInformation(),
            referenceApplicationCmd.getPersonalData(),
            referenceApplicationCmd.getServiceClass(),
            referenceApplicationCmd.getAvailability(),
            referenceApplicationCmd.getRpo(),
            referenceApplicationCmd.getRto());
        application.updateCriticity(criticity);

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

    @Override
    public void referenceOrUpdateCollectionOfApplication(CollectionApplicationCmd collectionApplicationCmd) {
        ImportFlux importFlux = repositoryOfFluxRefi.retieveByFluxName(collectionApplicationCmd.getImportName());

        for (ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd : collectionApplicationCmd.getApplicationCmdCollection()) {
            importFlux.getStatUpdateApplication().referenceResult(referenceOrUpdateApplication(referenceApplicationCmd));
        }

        repositoryOfFluxRefi.save(importFlux);
    }
}
