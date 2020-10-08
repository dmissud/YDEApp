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
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase;

@Configuration
@EnableBatchProcessing
public class RefiFileImportJob {

    public static final String IMPORT_REFI_STEP = "import-refi-step";
    public static final String IMPORT_REFI_JOB = "import-refi-job";

    @Autowired
    StoreFileRefiUseCase storeFileRefiUseCase;

    @Autowired
    JobLauncher jobLauncher;

    private static final Logger log = LoggerFactory.getLogger(RefiFileImportJob.class);

    @Bean
    @Autowired
    public Job importJob(StepBuilderFactory stepBuilderFactory,
                         JobBuilderFactory jobBuilderFactory,
                         RefiJobListener refiJobListener,
                         ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase) {
        log.trace("import-job created");
        return jobBuilderFactory.get(IMPORT_REFI_JOB)
            .incrementer(new RunIdIncrementer()) //
            .listener(refiJobListener)
            .start(importStep(stepBuilderFactory, referenceCollectionOfApplicationUseCase)) //
            .build();
    }

    @Bean
    public Step importStep(StepBuilderFactory stepBuilderFactory, ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase) {
        return stepBuilderFactory.get(IMPORT_REFI_STEP) //
            .<ApplicationSourcePosition, ReferenceApplicationUseCase.ReferenceApplicationCmd>chunk(5) //
            .reader(buildRefiItemReader())
            .faultTolerant()
            .skipPolicy(fileVerificationSkipper())//
            .processor(processor())
            .writer(ydeAppWriterBuilder(referenceCollectionOfApplicationUseCase)) //
            .build();
    }

    @Bean
    public SkipPolicy fileVerificationSkipper() {
        return new FileVerificationSkipper();
    }

    @Bean
    FlatFileItemReader<ApplicationSourcePosition> buildRefiItemReader() {
        RefiReaderBuilder refiReaderBuilder = new RefiReaderBuilder();

        return refiReaderBuilder.build();
    }

    @Bean
    ItemProcessor<ApplicationSourcePosition, ReferenceApplicationUseCase.ReferenceApplicationCmd> processor() {
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
    YdeAppWriter ydeAppWriterBuilder(ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase) {
        return new YdeAppWriter(referenceCollectionOfApplicationUseCase);
    }

}
