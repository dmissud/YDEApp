package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.application.in.flux.RefiImportQuery;
import org.yde.ydeapp.application.in.flux.StoreFileRefiUseCase;
import org.yde.ydeapp.application.in.flux.ReportImportFluxUseCase;
import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;
import org.yde.ydeapp.domain.flux.Job;
import org.yde.ydeapp.domain.out.RepositoryOfFluxRefi;

import java.util.List;

@Component
public class RepositoryOfRefiFileService implements StoreFileRefiUseCase, ReportImportFluxUseCase, RefiImportQuery {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfRefiFileService.class);

    @Autowired
    RepositoryOfFluxRefi repositoryOfFluxRefi;

    @Override
    public Long importRefiFlux(ImportRefiFluxCmd importRefiFluxCmd) {
        Long jobId = repositoryOfFluxRefi.referenceFlux(importRefiFluxCmd.getInputStream());
        ImportFlux importFlux = new ImportFlux(importRefiFluxCmd.getOriginalName(), jobId);

        repositoryOfFluxRefi.realize(importFlux);

        log.trace("launch of import job for file : {}", importFlux.getOrigninalName());
        return jobId;
    }

    @Override
    public void reportImportFlux(ReportImportFluxCmd reportImportFluxCmd) {
        ImportFlux importFlux = repositoryOfFluxRefi.retieveByFluxName(reportImportFluxCmd.getFluxName());
        Job job = new Job(reportImportFluxCmd.getReadCount(),
            reportImportFluxCmd.getStatus(),
            reportImportFluxCmd.getEndStatus(),
            reportImportFluxCmd.getResult(),
            reportImportFluxCmd.getStart(),
            reportImportFluxCmd.getDuration());
        importFlux.setJob(job);
        repositoryOfFluxRefi.save(importFlux);
    }

    @Override
    public ImportFlux getImportFlux(Long ifOfImportFlux) {
        return repositoryOfFluxRefi.retieveByFluxId(ifOfImportFlux);
    }

    @Override
    public List<ImportFluxIdent> getAllImportFlux() {
        return repositoryOfFluxRefi.getAllImportFluxId();
    }
}


