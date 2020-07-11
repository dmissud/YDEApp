package org.yde.ydeapp.application.in;

import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;

import java.util.List;

public interface GetApplicationQuery {
    Application getApplication(String codeApplication);

    List<ApplicationIdent> getAllApplicationsIdent();
}
