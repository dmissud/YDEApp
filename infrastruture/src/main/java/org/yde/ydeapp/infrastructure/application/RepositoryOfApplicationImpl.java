package org.yde.ydeapp.infrastructure.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

@Repository
public class RepositoryOfApplicationImpl implements RepositoryOfApplication {

    @Autowired
    RepositoryOfApplicationJpa repositoryOfApplicationJpa;

    @Override
    public Application retrieveByAppCode(String codeApp) {
        ApplicationEntity applicationEntity = repositoryOfApplicationJpa.findByCodeApp(codeApp);
        if (applicationEntity == null) {
            throw new EntityNotFound(String.format("Application with %s is not in repository", codeApp));
        }
        return new Application.Builder(applicationEntity.getCodeApp())
            .withShortDescription(applicationEntity.getShortDescription())
            .withLongDescription(applicationEntity.getLongDescription())
            .withResponsable(applicationEntity.getNameOfResponsable())
            .build();
    }


    @Override
    public void referenceApplication(Application application) {
// Not yet Implemented
    }
}
