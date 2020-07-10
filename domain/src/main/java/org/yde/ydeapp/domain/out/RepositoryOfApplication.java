package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.Application;

public interface RepositoryOfApplication {
    Application retrieveByAppCode(String codeApp);

    void referenceApplication(Application application);
}
