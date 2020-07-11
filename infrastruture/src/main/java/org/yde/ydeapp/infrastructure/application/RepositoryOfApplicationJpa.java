package org.yde.ydeapp.infrastructure.application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryOfApplicationJpa extends JpaRepository<ApplicationEntity, Long> {
    ApplicationEntity findByCodeApp(String codeApp);
}
