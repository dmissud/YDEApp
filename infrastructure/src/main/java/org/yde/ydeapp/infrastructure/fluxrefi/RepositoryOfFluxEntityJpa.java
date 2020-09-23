package org.yde.ydeapp.infrastructure.fluxrefi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryOfFluxEntityJpa extends JpaRepository<FluxEntity, Long> {
    List<FluxEntity> findFluxEntitiesByLocation(String location);
}

