package org.yde.ydeapp.infrastructure.fluxrefi;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;
import org.yde.ydeapp.domain.flux.Job;
import org.yde.ydeapp.domain.flux.StatUpdateApplications;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfFluxRefi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Component
public class FileRefiRepositoryImpl implements RepositoryOfFluxRefi {
    public static final String FLUX_S_NOT_FOUND = "flux %s not found";
    private final ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration;
    private final RepositoryOfFluxEntityJpa repositoryOfFluxEntityJpa;

    private static final Logger log = LoggerFactory.getLogger(FileRefiRepositoryImpl.class);
    private Path fileStorageLocation = null;
    private Path fileUploadLocation = null;


    @Autowired
    public FileRefiRepositoryImpl(ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration,
                                  RepositoryOfFluxEntityJpa repositoryOfFluxEntityJpa) {
        this.reposiroryFluxRefiConfiguration = reposiroryFluxRefiConfiguration;
        this.repositoryOfFluxEntityJpa = repositoryOfFluxEntityJpa;
        initFileRepository();
    }

    @Override
    public void referenceFlux(ImportFlux importFlux, InputStream flux) {
        String fluxTechName = storeFileInRepertoire(flux);
        importFlux.setLocation(fluxTechName);
        FluxEntity fluxEntity = new FluxEntity();
        mapImportFluxToFluxEntity(importFlux, fluxEntity);
        repositoryOfFluxEntityJpa.save(fluxEntity);
        importFlux.setFluxId(fluxEntity.getId());
    }

    @Override
    public void realize(ImportFlux importFlux) {
        Optional<FluxEntity> fluxEntityOptional = repositoryOfFluxEntityJpa.findById(importFlux.getFluxId());
        FluxEntity fluxEntity = fluxEntityOptional.orElseThrow(() -> new EntityNotFound(String.format(FLUX_S_NOT_FOUND, importFlux.getFluxId())));
        Path targetLocation = this.fileUploadLocation.resolve(fluxEntity.getLocation());
        Path sourceLocation = this.fileStorageLocation.resolve(fluxEntity.getLocation());
        fluxEntity.setOrignalFileName(importFlux.getOriginalName());
        fluxEntity.setCreateDate(importFlux.getCreateDate());
        repositoryOfFluxEntityJpa.save(fluxEntity);
        try {
            Files.copy(sourceLocation, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Could not copy the file to exec batch with error : %s", e.getMessage()));
        }
    }

    @Override
    public ImportFlux retrieveByFluxName(String fluxName) {
        List<FluxEntity> fluxEntities = repositoryOfFluxEntityJpa.findFluxEntitiesByLocation(fluxName);
        if (fluxEntities.size() != 1) {
            throw new EntityNotFound(String.format("Flux %s not present in the repository", fluxName));
        }
        return mapFluxEntityToImportFlux(fluxEntities.get(0));
    }

    @Override
    public void save(ImportFlux importFlux) {
        Optional<FluxEntity> fluxEntityOptional = repositoryOfFluxEntityJpa.findById(importFlux.getFluxId());
        FluxEntity fluxEntity = fluxEntityOptional.orElseThrow(() -> new EntityNotFound(String.format(FLUX_S_NOT_FOUND, importFlux.getFluxId())));

        mapImportFluxToFluxEntity(importFlux, fluxEntity);

        repositoryOfFluxEntityJpa.save(fluxEntity);
    }

    @Override
    public ImportFlux retieveByFluxId(Long ifOfImportFlux) {
        return mapFluxEntityToImportFlux(repositoryOfFluxEntityJpa.findById(ifOfImportFlux)
            .orElseThrow(() -> new EntityNotFound(String.format(FLUX_S_NOT_FOUND, ifOfImportFlux))));
    }

    @Override
    public List<ImportFluxIdent> getAllImportFluxId() {
        return repositoryOfFluxEntityJpa.retrieveAllImportFluxIdent();
    }

    @Override
    public void deleteByFluxId(Long idOfImportFlux) {
        Optional<FluxEntity> fluxEntity = repositoryOfFluxEntityJpa.findById(idOfImportFlux);
        repositoryOfFluxEntityJpa.delete(fluxEntity.orElseThrow(() -> new EntityNotFound(String.format("%s n'existe pas", idOfImportFlux))));
        Path pathLocation = this.fileStorageLocation.resolve(fluxEntity.get().getLocation());
        try {
            Files.delete(pathLocation);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Could not delete the location file with error : %s", e.getMessage()));
        }
    }

    private ImportFlux mapFluxEntityToImportFlux(FluxEntity fluxEntity) {
        ImportFlux importFlux =
            new ImportFlux(fluxEntity.getOrignalFileName(), fluxEntity.getId(), fluxEntity.getFluxState(), fluxEntity.getCreateDate());
        importFlux.setLocation(fluxEntity.getLocation());
        importFlux.setJob(new Job(fluxEntity.getJobInfoEntity().getReadCount(),
            fluxEntity.getJobInfoEntity().getStatus(),
            fluxEntity.getJobInfoEntity().getEndStatus(),
            fluxEntity.getJobInfoEntity().getResultDescription(),
            fluxEntity.getJobInfoEntity().getStartJob(),
            fluxEntity.getJobInfoEntity().getDuration()));
        importFlux.setStatUpdateApplication(new StatUpdateApplications(
            fluxEntity.getStatUpdateEntity().getReferenceCounter(),
            fluxEntity.getStatUpdateEntity().getUpdateCounter(),
            fluxEntity.getStatUpdateEntity().getIgnoreCounter(),
            fluxEntity.getStatUpdateEntity().getNoMoreUpdated()
        ));
        return importFlux;
    }

    private void mapImportFluxToFluxEntity(ImportFlux importFlux, FluxEntity fluxEntity) {
        StatUpdateEntity statUpdateEntity = new StatUpdateEntity();
        statUpdateEntity.setIgnoreCounter(importFlux.getStatUpdateApplication().getIgnoreCounter());
        statUpdateEntity.setNoMoreUpdated(importFlux.getStatUpdateApplication().getNoMoreUpdated());
        statUpdateEntity.setUpdateCounter(importFlux.getStatUpdateApplication().getUpdateCounter());
        statUpdateEntity.setReferenceCounter(importFlux.getStatUpdateApplication().getReferenceCounter());
        fluxEntity.setStatUpdateEntity(statUpdateEntity);

        JobInfoEntity jobInfoEntity = new JobInfoEntity();
        jobInfoEntity.setReadCount(importFlux.getJob().getReadCount());
        jobInfoEntity.setStatus(importFlux.getJob().getJobStatus());
        jobInfoEntity.setEndStatus(importFlux.getJob().getExistStatus());
        jobInfoEntity.setResultDescription(importFlux.getJob().getResultDescription());
        jobInfoEntity.setStartJob(importFlux.getJob().getStartDate());
        jobInfoEntity.setDuration(importFlux.getJob().getDuration());
        fluxEntity.setJobInfoEntity(jobInfoEntity);

        fluxEntity.setOrignalFileName(importFlux.getOriginalName());
        fluxEntity.setLocation(importFlux.getLocation());
        fluxEntity.setFluxState(importFlux.getFluxState());
        fluxEntity.setCreateDate(importFlux.getCreateDate());
    }

    private String storeFileInRepertoire(InputStream flux) {
        String techFluxName = RandomStringUtils.randomAlphabetic(15) + ".csv";
        Path targetLocation = this.fileStorageLocation.resolve(techFluxName);
        try {
            Files.copy(flux, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Could not copy the receive file with error : %s", e.getMessage()));
        }
        return techFluxName;
    }

    private void initFileRepository() {
        try {
            this.fileStorageLocation = Paths.get(reposiroryFluxRefiConfiguration.getStoreDir()).toAbsolutePath().normalize();
            Path fileWorkLocation = Paths.get(reposiroryFluxRefiConfiguration.getWorkDir()).toAbsolutePath().normalize();
            this.fileUploadLocation = Paths.get(reposiroryFluxRefiConfiguration.getUploadDir()).toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(fileWorkLocation);
            Files.createDirectories(this.fileUploadLocation);
            log.info("UploadDir = {}", reposiroryFluxRefiConfiguration.getUploadDir());
        } catch (Exception ex) {
            log.error("Could not create the directory {} where the uploaded files will be stored", reposiroryFluxRefiConfiguration.getUploadDir());
            throw new FileStorageException(String.format("Could not create the directory %s where the uploaded files will be stored.", this.fileStorageLocation));
        }
    }
}
