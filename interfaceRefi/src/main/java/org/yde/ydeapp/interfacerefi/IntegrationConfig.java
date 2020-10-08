package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
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

    private static Logger log = LoggerFactory.getLogger(IntegrationConfig.class);

    protected DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    @Autowired
    public IntegrationFlow integrationFlow(ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration,
                                           JobLauncher jobLauncher,
                                           Job refiJob) {
        return IntegrationFlows
            .from(fileReadingMessageSource(reposiroryFluxRefiConfiguration),
                c -> c.poller(Pollers.fixedDelay(2500).maxMessagesPerPoll(1)))
            .channel(inputChannel())
            .transform(fileMessageToJobRequest(refiJob))
            .handle(jobLaunchingMessageHandler(jobLauncher)) //
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
    @Autowired
    JobLaunchingMessageHandler jobLaunchingMessageHandler(JobLauncher jobLauncher) {
        return new JobLaunchingMessageHandler(jobLauncher);
    }

    @Bean
    @Autowired
    public FileMessageToJobRequest fileMessageToJobRequest(Job refiJob) {
        FileMessageToJobRequest fileMessageToJobRequest = new FileMessageToJobRequest();
        fileMessageToJobRequest.setFileParameterName("refi_file_name");
        fileMessageToJobRequest.setJob(refiJob);
        return fileMessageToJobRequest;
    }
}
