package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.yde.ydeapp.application.in.ReferenceCollectionOfApplicationUseCase;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class RepositoryOfRefiFileService implements StoreFileRefi {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfRefiFileService.class);
    public static final String PROPERTY_FILE_NAME = "fileName";

    @Autowired
    RefiFileImportJob refiFileImportJob;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobOperator jobOperator;

    @Autowired
    ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;

    @Autowired
    FileRefiRepository fileRefiRepository;

    private MultipartFile refiFile = null;

    @Override
    public StatusFile storeRefiFile(MultipartFile refiFile) {

        RefiFileDesc refiFileDesc = fileRefiRepository.storeRefiFile(refiFile);
        try {
            jobLauncher.run(refiFileImportJob.importJob(), new JobParametersBuilder()
                .addString(PROPERTY_FILE_NAME, refiFileDesc.getFileLocation())
                .toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        if(refiFile.isEmpty()){
            return StatusFile.FILEKO;
        }
        return StatusFile.FILEOK;
    }

    @Override
    public MultipartFile retrieveRefiFile() {
        return refiFile;
    }

    public TransformerSourceToCmd giveTransformerSourceToCmd() {

        try {
            return new TransformerSourceToCmd
                    (new InputStreamReader(refiFile.getInputStream(), StandardCharsets.ISO_8859_1));

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return null;
    }
}


