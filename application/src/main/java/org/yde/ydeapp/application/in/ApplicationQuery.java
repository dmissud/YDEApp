package org.yde.ydeapp.application.in;

import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;
import org.yde.ydeapp.domain.Note;

import java.util.List;

public interface ApplicationQuery {
    Application getApplication(String codeApplication);

    List<ApplicationIdent> getAllApplicationsIdent();

}