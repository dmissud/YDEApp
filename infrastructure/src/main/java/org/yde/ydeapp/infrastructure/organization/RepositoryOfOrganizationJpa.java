package org.yde.ydeapp.infrastructure.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.util.List;

public interface RepositoryOfOrganizationJpa extends JpaRepository<OrganizationEntity, Long> {
    OrganizationEntity findByIdRefog(String nameOfOrganization);
    @Query(value = "select new org.yde.ydeapp.domain.organization.OrganizationIdent(orga.idRefog, orga.name) from OrganizationEntity orga where orga.idRefog = :idRefog")
    OrganizationIdent retrieveOrganizationIdent(String idRefog);
    List<OrganizationEntity> findAllByRootIs(boolean isRoot);
    @Query(value = "SELECT new org.yde.ydeapp.domain.organization.OrganizationIdent(organization.idRefog, organization.name) FROM OrganizationEntity organization")
    List<OrganizationIdent> findAllOrganizationIdent();
}
