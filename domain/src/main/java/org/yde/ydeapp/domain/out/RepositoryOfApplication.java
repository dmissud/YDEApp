package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;

import java.util.List;

public interface RepositoryOfApplication {
    Application retrieveByAppCode(String codeApp);

    void referenceApplication(Application application);

    List<ApplicationIdent> retrieveIdentOfAllApplications();
}
