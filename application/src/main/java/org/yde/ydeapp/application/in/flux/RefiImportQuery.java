package org.yde.ydeapp.application.in.flux;

import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;

import java.util.List;

public interface RefiImportQuery {
    ImportFlux getImportFlux(Long idOfImportFlux);

    List<ImportFluxIdent> getAllImportFlux();

    void deleteImportFlux(Long idOfImportFlux);
}
