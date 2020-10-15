package org.yde.ydeapp.infrastructure.fluxrefi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;

import java.util.List;

public interface RepositoryOfFluxEntityJpa extends JpaRepository<FluxEntity, Long> {
    List<FluxEntity> findFluxEntitiesByLocation(String location);
    @Query(value = "SELECT new org.yde.ydeapp.domain.flux.ImportFluxIdent(flux.id, " +
        "flux.orignalFileName, " +
        "flux.fluxState, " +
        "flux.createDate, " +
        "flux.jobInfoEntity.status) FROM FluxEntity flux ")
    List<ImportFluxIdent> retrieveAllImportFluxIdent();

}

