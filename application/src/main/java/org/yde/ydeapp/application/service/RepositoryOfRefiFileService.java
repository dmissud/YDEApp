package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase;
import org.yde.ydeapp.domain.flux.ImportJob;
import org.yde.ydeapp.domain.out.RepositoryOfFluxRefi;

@Component
public class RepositoryOfRefiFileService implements StoreFileRefiUseCase {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfRefiFileService.class);
    public static final String PROPERTY_FILE_NAME = "fileName";


    @Autowired
    RepositoryOfFluxRefi repositoryOfFluxRefi;

    @Override
    public Long importRefiFlux(ImportRefiFluxCmd importRefiFluxCmd) {
        Long jobId = repositoryOfFluxRefi.referenceFlux(importRefiFluxCmd.getInputStream());
        ImportJob importJob = new ImportJob(importRefiFluxCmd.getOriginalName(), jobId);

        repositoryOfFluxRefi.realize(importJob);

        log.trace("launch of import job for file : {}", importJob.getOrigninalName());
        return jobId;
    }
}


