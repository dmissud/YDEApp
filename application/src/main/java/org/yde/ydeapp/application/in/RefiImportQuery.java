package org.yde.ydeapp.application.in;

import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;

import java.util.List;

public interface RefiImportQuery {
    ImportFlux getImportFlux(Long ifOfImportFlux);

    List<ImportFluxIdent> getAllImportFlux();
}
