package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.flux.ImportJob;

import java.io.InputStream;

public interface RepositoryOfFluxRefi {
    Long referenceFlux(InputStream flux);
    void realize(ImportJob importJob);
}
