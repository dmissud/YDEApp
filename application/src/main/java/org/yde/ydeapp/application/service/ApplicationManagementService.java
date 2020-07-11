package org.yde.ydeapp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

@Service
@Transactional
public class ApplicationManagementService implements ReferenceApplicationUseCase {

    @Autowired
    RepositoryOfApplication repositoryOfApplication;

    @Override
    public Application referenceApplication(ReferenceApplicationCmd referenceApplicationCmd) {
        Application application = repositoryOfApplication.retrieveByAppCode(referenceApplicationCmd.getCodeApp());
        if (application != null) {
            throw new EntityAlreadyExist(String.format("Application %s all ready exist", referenceApplicationCmd.getCodeApp()));
        }
        application = new Application.Builder(referenceApplicationCmd.getCodeApp())
            .withShortDescription(referenceApplicationCmd.getShortDescription())
            .withLongDescription(referenceApplicationCmd.getLongDescription())
            .withResponsable(referenceApplicationCmd.getNameOfResponsable())
            .build();
        repositoryOfApplication.referenceApplication(application);
        return application;
    }
}
