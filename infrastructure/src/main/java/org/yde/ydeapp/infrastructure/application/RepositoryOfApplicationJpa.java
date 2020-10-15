package org.yde.ydeapp.infrastructure.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yde.ydeapp.domain.application.ApplicationIdent;

import java.util.List;

public interface RepositoryOfApplicationJpa extends JpaRepository<ApplicationEntity, Long> {
    ApplicationEntity findByCodeApp(String codeApp);

    @Query(value = "SELECT new org.yde.ydeapp.domain.application.ApplicationIdent(app.codeApp, app.shortDescription) FROM ApplicationEntity app ")
    List<ApplicationIdent> retrieveApplicationsIdent();
}
