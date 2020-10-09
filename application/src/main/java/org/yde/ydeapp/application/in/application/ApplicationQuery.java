package org.yde.ydeapp.application.in.application;

import org.yde.ydeapp.domain.application.Application;
import org.yde.ydeapp.domain.application.ApplicationIdent;

import java.util.List;

public interface ApplicationQuery {
    Application getApplication(String codeApplication);

    List<ApplicationIdent> getAllApplicationsIdent();

}
