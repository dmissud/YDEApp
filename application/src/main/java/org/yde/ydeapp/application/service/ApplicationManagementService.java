package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.GetApplicationQuery;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.List;

@Service
@Transactional
public class ApplicationManagementService implements ReferenceApplicationUseCase, GetApplicationQuery {

    private final Logger log = LoggerFactory.getLogger(ApplicationManagementService.class);

    @Autowired
    RepositoryOfApplication repositoryOfApplication;

    @Override
    public Application referenceApplication(ReferenceApplicationCmd referenceApplicationCmd) {

        Application application = new Application.Builder(referenceApplicationCmd.getCodeApp())
            .withShortDescription(referenceApplicationCmd.getShortDescription())
            .withLongDescription(referenceApplicationCmd.getLongDescription())
            .withResponsable(referenceApplicationCmd.getNameOfResponsable())
            .build();
        repositoryOfApplication.referenceApplication(application);
        log.trace("Application {} referenced", application.getCodeApplication());

        return application;
    }

    @Override
    public void updateApplication(ReferenceApplicationCmd referenceApplicationCmd) {
        Application application = getApplication(referenceApplicationCmd.getCodeApp());
        application.setLongDescription(referenceApplicationCmd.getLongDescription());
        application.setNameOfResponsable(referenceApplicationCmd.getNameOfResponsable());
        application.setShortDescription(referenceApplicationCmd.getShortDescription());

        repositoryOfApplication.updateApplication(application);
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
