package org.yde.ydeapp.interfacerefi;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Component
public class FileRefiRepository {
    private final YdeAppReposiroryConfiguration ydeAppReposiroryConfiguration;
    private static final Logger log = LoggerFactory.getLogger(FileRefiRepository.class);
    private Path fileStorageLocation = null;

    @Autowired
    public FileRefiRepository(YdeAppReposiroryConfiguration ydeAppReposiroryConfiguration) {
        this.ydeAppReposiroryConfiguration = ydeAppReposiroryConfiguration;
        initFileRepository();
    }

    private void initFileRepository() {
        this.fileStorageLocation = Paths.get(ydeAppReposiroryConfiguration.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("UploadDir = {}", ydeAppReposiroryConfiguration.getUploadDir());
        } catch (Exception ex) {
            log.error("Could not create the directory {} where the uploaded files will be stored", ydeAppReposiroryConfiguration.getUploadDir());
            throw new FileStorageException(String.format("Could not create the directory %s where the uploaded files will be stored.", this.fileStorageLocation));
        }
    }

    public RefiFileDesc storeRefiFile(MultipartFile refiFile) {
        RefiFileDesc refiFileDesc = new RefiFileDesc();
        try {
            refiFileDesc.setOriginalFileName(StringUtils.cleanPath(Objects.requireNonNull(refiFile.getOriginalFilename())));
            refiFileDesc.setFileLocation(storeFileInRepertoire(refiFile).toString());
            log.info("Add Flux {} to repository on the name {}",
                refiFileDesc.getOriginalFileName(),
                refiFileDesc.getFileLocation());
            return refiFileDesc;
        } catch (IOException ex) {
            log.error("Could not store file {}", refiFileDesc.getOriginalFileName());
            throw new FileStorageException(String.format("Could not store file %s. Please try again!",
                refiFileDesc.getOriginalFileName()));
        }
    }

    private Path storeFileInRepertoire(MultipartFile refiFile) throws IOException {
        Path targetLocation = this.fileStorageLocation.resolve(RandomStringUtils.randomAlphabetic(15));
        Files.copy(refiFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return targetLocation;
    }
}
