package org.yde.ydeapp.interfacerefi;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.application.in.ReportImportFluxUseCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class RefiJobListener implements JobExecutionListener {

    private final ReportImportFluxUseCase reportImportFluxUseCase;

    @Autowired
    private YdeAppWriter ydeAppWriter;

    @Autowired
    FlatFileItemReader<ApplicationSourcePosition> buildRefiItemReader;

    private Path pathRefiFileToImport;

    @Autowired
    public RefiJobListener(ReportImportFluxUseCase reportImportFluxUseCase) {
        this.reportImportFluxUseCase = reportImportFluxUseCase;
        this.pathRefiFileToImport = null;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String keyName = jobExecution.getJobParameters().getString("refi_file_name");
        if (keyName != null) {
            this.pathRefiFileToImport = Paths.get(keyName);
            ydeAppWriter.workOn(this.pathRefiFileToImport);
            buildRefiItemReader.setResource(new FileSystemResource(this.pathRefiFileToImport));
        } else {
            this.pathRefiFileToImport = null;
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (this.pathRefiFileToImport != null) {
            int readCount = 0;
            for (StepExecution v : jobExecution.getStepExecutions()) {
                if (v.getStepName().equals(RefiFileImportJob.IMPORT_REFI_STEP)) {
                    readCount = v.getReadCount();
                }
            }
            LocalDateTime start = jobExecution.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime stop = jobExecution.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            ReportImportFluxUseCase.ReportImportFluxCmd reportImportFluxCmd = new ReportImportFluxUseCase.ReportImportFluxCmd(
                this.pathRefiFileToImport.getFileName().toString(),
                jobExecution.getStatus().toString(),
                jobExecution.getExitStatus().getExitCode(),
                jobExecution.getExitStatus().getExitDescription(),
                start,
                Duration.between(start, stop),
                readCount
            );

            reportImportFluxUseCase.reportImportFlux(reportImportFluxCmd);
            try {
                Files.delete(this.pathRefiFileToImport);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
