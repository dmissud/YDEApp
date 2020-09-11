package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    StoreFileRefi storeFileRefi;
    @Autowired
    RefiReader refiReader;

    @Autowired
    JobRepository jobRepository;

    private static final Logger log = LoggerFactory.getLogger(RefiFileImportJob.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Job importJob() {
        log.trace("import-job created");
        return jobBuilderFactory.get("import-job"+ LocalDateTime.now().toString())
            .incrementer(new RunIdIncrementer()) //
            .listener(refiReader)
            .start(importStep(stepBuilderFactory, ydeAppWriter)) //
            .build();
    }


    public Step importStep(StepBuilderFactory stepBuilderFactory, YdeAppWriter ydeAppWriter) {
        return stepBuilderFactory.get("import-step"+ LocalDateTime.now().toString()) //
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

}
