package org.yde.ydeapp.infrastructure.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yde.ydeapp.domain.OrganizationIdent;

public interface RepositoryOfOrganizationJpa extends JpaRepository<OrganizationEntity, Long> {
    OrganizationEntity findByIdRefog(String nameOfOrganization);
    @Query(value = "select new org.yde.ydeapp.domain.OrganizationIdent(orga.idRefog, orga.name) from OrganizationEntity orga where orga.idRefog = :idRefog")
    OrganizationIdent retrieveOrganizationIdent(String idRefog);
}
