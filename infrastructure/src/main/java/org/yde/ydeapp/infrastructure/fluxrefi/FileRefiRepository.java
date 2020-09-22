package org.yde.ydeapp.infrastructure.fluxrefi;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.domain.flux.ImportJob;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfFluxRefi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Component
public class FileRefiRepository implements RepositoryOfFluxRefi {
    private final ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration;
    private final RepositoryOfFluxJpa repositoryOfFluxJpa;

    private static final Logger log = LoggerFactory.getLogger(FileRefiRepository.class);
    private Path fileStorageLocation = null;
    private Path fileOldStorageLocation = null;


    @Autowired
    public FileRefiRepository(ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration,
                              RepositoryOfFluxJpa repositoryOfFluxJpa) {
        this.reposiroryFluxRefiConfiguration = reposiroryFluxRefiConfiguration;
        this.repositoryOfFluxJpa = repositoryOfFluxJpa;
        initFileRepository();
    }

    @Override
    public Long referenceFlux(InputStream flux) {
        String fluxTechName = storeFileInRepertoire(flux);
        FluxEntity fluxEntity = storeFluxEntity(fluxTechName);
        return fluxEntity.getId();
    }

    @Override
    public void realize(ImportJob importJob) {
        Optional<FluxEntity> fluxEntityOptional = repositoryOfFluxJpa.findById(importJob.getFluxId());
        FluxEntity fluxEntity = fluxEntityOptional.orElseThrow(() -> new EntityNotFound(String.format("flux %s not found", importJob.getFluxId())));
        Path targetLocation = this.fileStorageLocation.resolve(fluxEntity.getLocation());
        Path sourceLocation = this.fileOldStorageLocation.resolve(fluxEntity.getLocation());
        try {
            Files.copy(sourceLocation, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Could not copy the file to exec batch with error : %s", e.getMessage()));
        }
    }

    private FluxEntity storeFluxEntity(String fluxTechName) {
        FluxEntity fluxEntity = new FluxEntity();
        fluxEntity.setLocation(fluxTechName);

        fluxEntity = repositoryOfFluxJpa.save(fluxEntity);
        return fluxEntity;
    }

    private String storeFileInRepertoire(InputStream flux) {
        String techFluxName = RandomStringUtils.randomAlphabetic(15) + ".csv";
        Path targetLocation = this.fileOldStorageLocation.resolve(techFluxName);
        try {
            Files.copy(flux, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Could not copy the receive file with error : %s", e.getMessage()));
        }
        return techFluxName;
    }

    private void initFileRepository() {
        this.fileStorageLocation = Paths.get(reposiroryFluxRefiConfiguration.getUploadDir()).toAbsolutePath().normalize();
        this.fileOldStorageLocation = Paths.get(reposiroryFluxRefiConfiguration.getOldDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.fileOldStorageLocation);
            log.info("UploadDir = {} OldDir = {}", reposiroryFluxRefiConfiguration.getUploadDir(), reposiroryFluxRefiConfiguration.getOldDir());
        } catch (Exception ex) {
            log.error("Could not create the directory {} where the uploaded files will be stored", reposiroryFluxRefiConfiguration.getUploadDir());
            throw new FileStorageException(String.format("Could not create the directory %s where the uploaded files will be stored.", this.fileStorageLocation));
        }
    }

}
