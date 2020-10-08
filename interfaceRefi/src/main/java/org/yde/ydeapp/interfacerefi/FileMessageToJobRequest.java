package org.yde.ydeapp.interfacerefi;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.yde.ydeapp.domain.out.BusinessException;
import org.yde.ydeapp.infrastructure.fluxrefi.ReposiroryFluxRefiConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileMessageToJobRequest {

    @Autowired
    private ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration;

    private Job job;
    private String fileParameterName;

    public void setFileParameterName(String fileParameterName) {
        this.fileParameterName = fileParameterName;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Transformer
    public JobLaunchRequest toRequest(Message<File> message) {
        JobParametersBuilder jobParametersBuilder =
            new JobParametersBuilder();
        File filePath = message.getPayload();
        jobParametersBuilder.addString(fileParameterName,
            moveFileInWorkPlace(filePath));
        jobParametersBuilder.addDate("dummy", new Date());

        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }

    private String moveFileInWorkPlace(File nameOfFile) {
        Path fileWorkLocation = Paths.get(reposiroryFluxRefiConfiguration.getWorkDir());
        Path targetLocation = fileWorkLocation.resolve(nameOfFile.getName());
        try {
            Files.move(nameOfFile.toPath(), targetLocation);
            return targetLocation.toString();
        } catch (IOException e) {
            throw new BusinessException("File management is broked : "+e.getMessage());
        }
    }
}
