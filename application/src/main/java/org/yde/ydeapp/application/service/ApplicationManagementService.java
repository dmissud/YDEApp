package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.*;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.List;

@Service
@Transactional
public class ApplicationManagementService implements ReferenceApplicationUseCase, ApplicationQuery {

    private final Logger log = LoggerFactory.getLogger(ApplicationManagementService.class);

    @Autowired
    RepositoryOfApplication repositoryOfApplication;

    @Override
    public ResultOfCollection referenceOrUpdateCollectionOfApplication(CollectionApplicationCmd collectionApplicationCmd) {
        ResultOfCollection resultOfCollection = new ResultOfCollection();
        for (ReferenceApplicationCmd referenceApplicationCmd : collectionApplicationCmd) {
            StateCmdEnum stateCmdEnum = referenceOrUpdateApplication(referenceApplicationCmd);
            switch (stateCmdEnum) {
                case IGNORE:
                    resultOfCollection.AddIgnore();
                    break;
                case UPDATE:
                    resultOfCollection.AddUpdate();
                    break;
                case REFERENCE:
                    resultOfCollection.AddReference();
                    break;
                default:
                    break;

            }
        }
        return resultOfCollection;
    }

    @Override
    public StateCmdEnum referenceOrUpdateApplication(ReferenceApplicationCmd referenceApplicationCmd) {
        StateCmdEnum stateCmd;

        referenceApplicationCmd.validate();

        Personne personne = new Personne(referenceApplicationCmd.getUid(), referenceApplicationCmd.getFirstName(), referenceApplicationCmd.getLastName());

        Application application = repositoryOfApplication.retrieveByAppCode(referenceApplicationCmd.getCodeApp());
        if (application != null) {
            log.trace("Application {} updated", application.getCodeApplication());
            application.setLongDescription(referenceApplicationCmd.getLongDescription());
            application.setShortDescription(referenceApplicationCmd.getShortDescription());
            application.setResponsable(personne);
            repositoryOfApplication.updateApplication(application);
            stateCmd = StateCmdEnum.UPDATE;
        } else {
            application = new Application.Builder(referenceApplicationCmd.getCodeApp())
                .withShortDescription(referenceApplicationCmd.getShortDescription())
                .withLongDescription(referenceApplicationCmd.getLongDescription())
                .withResponsable(personne)
                .build();
            log.trace("Application {} created", application.getCodeApplication());
            repositoryOfApplication.referenceApplication(application);
            stateCmd = StateCmdEnum.REFERENCE;
        }
        return stateCmd;
    }

    @Override
    public Application updateApplication(String codeApplication, ReferenceApplicationCmd referenceApplicationCmd) {
        referenceApplicationCmd.validate();
        Application application = getApplication(codeApplication);
        application.setLongDescription(referenceApplicationCmd.getLongDescription());
        application.setShortDescription(referenceApplicationCmd.getShortDescription());

        Personne personne = new Personne(referenceApplicationCmd.getUid(), referenceApplicationCmd.getFirstName(), referenceApplicationCmd.getLastName());
        application.setResponsable(personne);

        repositoryOfApplication.updateApplication(application);

        return application;
    }

    @Override
    public Application getApplication(String codeApplication) {
        return repositoryOfApplication.retrieveByAppCode(codeApplication);
    }

    @Override
    public List<ApplicationIdent> getAllApplicationsIdent() {
        return repositoryOfApplication.retrieveIdentOfAllApplications();
    }
}
