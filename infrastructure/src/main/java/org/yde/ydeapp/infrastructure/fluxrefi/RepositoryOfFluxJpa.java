package org.yde.ydeapp.infrastructure.fluxrefi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryOfFluxJpa extends JpaRepository<FluxEntity, Long> {
}
