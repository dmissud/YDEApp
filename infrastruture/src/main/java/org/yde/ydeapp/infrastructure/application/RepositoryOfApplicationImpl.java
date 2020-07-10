package org.yde.ydeapp.infrastructure.application;

import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

@Repository
public class RepositoryOfApplicationImpl implements RepositoryOfApplication {
    @Override
    public Application retrieveByAppCode(String codeApp) {
        return null;
    }

    @Override
    public void referenceApplication(Application application) {

    }
}
