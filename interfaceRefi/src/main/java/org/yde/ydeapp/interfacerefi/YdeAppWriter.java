package org.yde.ydeapp.interfacerefi;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.application.in.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase.ReferenceApplicationCmd;
import org.yde.ydeapp.application.in.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.ReportImportFluxUseCase;
import org.yde.ydeapp.application.in.ReportImportFluxUseCase.ReportImportFluxCmd;
import org.yde.ydeapp.infrastructure.fluxrefi.ReposiroryFluxRefiConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Component
public class YdeAppWriter implements ItemWriter<ReferenceApplicationCmd>, JobExecutionListener {

    private final ReportImportFluxUseCase reportImportFluxUseCase;
    private final ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;

    private String nameOfFile;

    @Autowired
    public YdeAppWriter(ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase,
                        ReportImportFluxUseCase reportImportFluxUseCase) {
        this.referenceCollectionOfApplicationUseCase = referenceCollectionOfApplicationUseCase;
        this.reportImportFluxUseCase = reportImportFluxUseCase;
    }

    @Override
    public void write(List<? extends ReferenceApplicationCmd> listOfApplicationCmd) {
        referenceCollectionOfApplicationUseCase.referenceOrUpdateCollectionOfApplication(new CollectionApplicationCmd(listOfApplicationCmd, this.nameOfFile));
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        Path pathNameOfFile = Paths.get(Objects.requireNonNull(jobExecution.getJobParameters().getString(ReposiroryFluxRefiConfiguration.PROPERTY_FILE_NAME)));
        this.nameOfFile = pathNameOfFile.getFileName().toString();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        int readCount = 0;
        for (StepExecution v : jobExecution.getStepExecutions()) {
            if (v.getStepName().equals(RefiFileImportJob.IMPORT_REFI_STEP)) {
                readCount = v.getReadCount();
            }
        }
        LocalDateTime start = jobExecution.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime stop = jobExecution.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        ReportImportFluxCmd reportImportFluxCmd = new ReportImportFluxCmd(
            nameOfFile,
            jobExecution.getStatus().toString(),
            jobExecution.getExitStatus().getExitCode(),
            jobExecution.getExitStatus().getExitDescription(),
            start,
            Duration.between(start, stop),
            readCount
        );

        reportImportFluxUseCase.reportImportFlux(reportImportFluxCmd);
    }
}
