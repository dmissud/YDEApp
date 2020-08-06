package org.yde.ydeapp.infrastructure.application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryOfPersonneJpa extends JpaRepository<PersonneEntity, Long> {
    PersonneEntity findByUid(String uid);
}
