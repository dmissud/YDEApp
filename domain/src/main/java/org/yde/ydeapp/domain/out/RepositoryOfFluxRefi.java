package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.flux.ImportFlux;

import java.io.InputStream;

public interface RepositoryOfFluxRefi {
    Long referenceFlux(InputStream flux);
    void realize(ImportFlux importFlux);
    ImportFlux retieveByFluxName(String fluxName);
    void save(ImportFlux importFlux);

    ImportFlux retieveByFluxId(Long ifOfImportFlux);
}
