package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.yde.ydeapp.infrastructure.fluxrefi.ReposiroryFluxRefiConfiguration;

import java.io.File;

@Configuration
public class IntegrationConfig {

    @Autowired
    private JobLauncher jobLauncher;

    private static Logger log = LoggerFactory.getLogger(IntegrationConfig.class);

    protected DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Autowired
    private Job refiJob;

    @Bean
    @Autowired
    public IntegrationFlow integrationFlow(ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration) {
        return IntegrationFlows
            .from(fileReadingMessageSource(reposiroryFluxRefiConfiguration),
                c -> c.poller(Pollers.fixedDelay(2500).maxMessagesPerPoll(1)))
            .channel(inputChannel())
            .transform(fileMessageToJobRequest())
            .handle(jobLaunchingMessageHandler()) //
            .handle(jobExecution -> log.trace("JobExecution {}", jobExecution.getPayload()))
            .get();
    }

    @Bean
    public MessageSource<File> fileReadingMessageSource(ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration) {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(reposiroryFluxRefiConfiguration.getUploadDir()));
        source.setFilter(new SimplePatternFileListFilter("*.csv"));
        source.setUseWatchService(true);
        source.setWatchEvents(FileReadingMessageSource.WatchEventType.CREATE);
        return source;
    }

    @Bean
    JobLaunchingMessageHandler jobLaunchingMessageHandler() {
        return new JobLaunchingMessageHandler(jobLauncher);
    }

    @Bean
    public FileMessageToJobRequest fileMessageToJobRequest() {
        FileMessageToJobRequest fileMessageToJobRequest = new FileMessageToJobRequest();
        fileMessageToJobRequest.setFileParameterName("refi_file_name");
        fileMessageToJobRequest.setJob(refiJob);
        return fileMessageToJobRequest;
    }
}
