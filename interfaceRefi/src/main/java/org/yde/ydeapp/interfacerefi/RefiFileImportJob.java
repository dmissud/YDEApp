package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.handler.LoggingHandler;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase;
import org.yde.ydeapp.application.service.RepositoryOfRefiFileService;
import org.yde.ydeapp.infrastructure.fluxrefi.ReposiroryFluxRefiConfiguration;

import java.io.File;

@Configuration
@EnableBatchProcessing
public class RefiFileImportJob {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    YdeAppWriter ydeAppWriter;
    @Autowired
    StoreFileRefiUseCase storeFileRefiUseCase;
    @Autowired
    RefiReader refiReader;

    @Autowired
    JobLauncher jobLauncher;

    private static final Logger log = LoggerFactory.getLogger(RefiFileImportJob.class);

    public Job importJob() {
        log.trace("import-job created");
        return jobBuilderFactory.get("import-job")
            .incrementer(new RunIdIncrementer()) //
            .listener(refiReader)
            .start(importStep(stepBuilderFactory, ydeAppWriter)) //
            .build();
    }


    public Step importStep(StepBuilderFactory stepBuilderFactory, YdeAppWriter ydeAppWriter) {
        return stepBuilderFactory.get("import-step") //
            .<ApplicationSourcePosition, ReferenceApplicationUseCase.ReferenceApplicationCmd>chunk(5) //
            .reader(refiReader) //
            .processor(processor())
            .writer(ydeAppWriter) //
            .taskExecutor(taskExecutor())
            .build();
    }

    private ItemProcessor<ApplicationSourcePosition, ReferenceApplicationUseCase.ReferenceApplicationCmd> processor() {
        return item -> new ReferenceApplicationUseCase.ReferenceApplicationCmd(
            item.getCodeApp(),
            item.getShortLibelle(),
            item.getLongLibelle(),
            new ReferenceApplicationUseCase.ReferenceApplicationCmd.ResponsableCmd(
                item.getIdResponsableMOE(),
                item.getFirstNameResponsableMoe(),
                item.getLastNameResponsableMoe()),
            item.getIdRefogEntityMoe(),
            new ReferenceApplicationUseCase.ReferenceApplicationCmd.CycleLifeCmd(
                item.getState(),
                item.getDateOfCreation(),
                item.getDateOfLastUpdate(),
                item.getDateEndInReality()));
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("ydeapp_batch");
    }

    @Bean
    public FileMessageToJobRequest fileMessageToJobRequest() {
        FileMessageToJobRequest fileMessageToJobRequest = new FileMessageToJobRequest();
        fileMessageToJobRequest.setFileParameterName(RepositoryOfRefiFileService.PROPERTY_FILE_NAME);
        fileMessageToJobRequest.setJob(importJob());
        return fileMessageToJobRequest;
    }

    @Bean
    @Autowired
    public JobLaunchingGateway jobLaunchingGateway(JobRepository jobRepository) {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return new JobLaunchingGateway(simpleJobLauncher);
    }

    @Bean
    @Autowired
    public IntegrationFlow integrationFlow(JobLaunchingGateway jobLaunchingGateway, ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration) {
        return IntegrationFlows.from(Files.inboundAdapter(new File(reposiroryFluxRefiConfiguration.getUploadDir())).
                filter(new SimplePatternFileListFilter("*.csv")),
            c -> c.poller(Pollers.fixedRate(1000).maxMessagesPerPoll(1))).
            transform(fileMessageToJobRequest()).
            handle(jobLaunchingGateway).
            log(LoggingHandler.Level.WARN, "headers.id + ': ' + payload").
            get();
    }
}
