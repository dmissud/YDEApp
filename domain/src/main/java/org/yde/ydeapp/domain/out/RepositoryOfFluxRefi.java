package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;

import java.io.InputStream;
import java.util.List;

public interface RepositoryOfFluxRefi {
    void referenceFlux(ImportFlux importFlux, InputStream flux);
    void realize(ImportFlux importFlux);
    ImportFlux retrieveByFluxName(String fluxName);
    void save(ImportFlux importFlux);

    ImportFlux retieveByFluxId(Long ifOfImportFlux);

    List<ImportFluxIdent> getAllImportFluxId();

    void deleteByFluxId(Long idOfImportFlux);
}
