package org.yde.ydeapp.infrastructure.organization;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryOfOrganizationJpa extends JpaRepository<OrganizationEntity, Long> {
    OrganizationEntity findByName(String nameOfOrganization);

}
