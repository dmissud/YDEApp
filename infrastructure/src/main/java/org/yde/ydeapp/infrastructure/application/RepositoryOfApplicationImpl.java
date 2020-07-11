package org.yde.ydeapp.infrastructure.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.List;

@Repository
public class RepositoryOfApplicationImpl implements RepositoryOfApplication {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfApplicationImpl.class);

    @Autowired
    RepositoryOfApplicationJpa repositoryOfApplicationJpa;

    @Override
    public Application retrieveByAppCode(String codeApp) {
        ApplicationEntity applicationEntity = repositoryOfApplicationJpa.findByCodeApp(codeApp);
        if (applicationEntity == null) {
            log.debug("Application {} not exist", codeApp);
            throw new EntityNotFound(String.format("Application with %s is not in repository", codeApp));
        }
        log.debug("Application {} load", codeApp);
        return new Application.Builder(applicationEntity.getCodeApp())
            .withShortDescription(applicationEntity.getShortDescription())
            .withLongDescription(applicationEntity.getLongDescription())
            .withResponsable(applicationEntity.getNameOfResponsable())
            .build();
    }


    @Override
    public void referenceApplication(Application application) {
        ApplicationEntity applicationEntity = repositoryOfApplicationJpa.findByCodeApp(application.getCodeApplication());
        if (applicationEntity != null) {
            log.debug("Application {} with id {} already exist", applicationEntity.getCodeApp(), applicationEntity.getId());
            throw new EntityAlreadyExist(String.format("Application with %s is in repository", applicationEntity.getCodeApp()));
        }

        applicationEntity = new ApplicationEntity();
        applicationEntity.setCodeApp(application.getCodeApplication());
        applicationEntity.setShortDescription(application.getShortDescription());
        applicationEntity.setLongDescription(application.getLongDescription());
        applicationEntity.setNameOfResponsable(application.getNameOfResponsable());
        log.debug("Application {} create", application.getCodeApplication());

        repositoryOfApplicationJpa.save(applicationEntity);
    }

    @Override
    public List<ApplicationIdent> retrieveIdentOfAllApplications() {
        return repositoryOfApplicationJpa.retrieveApplicationsIdent();
    }
}
