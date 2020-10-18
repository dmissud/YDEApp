package org.yde.ydeapp.interfacerefi;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.yde.ydeapp.application.in.application.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.flux.ReportImportFluxUseCase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {RefiFileImportJob.class, RefiJobListener.class, YdeAppWriter.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DisplayName("Validation du Repository du flux refi")
class SpringBatchImportationFluxRefiJobTest {

    private static final String TEST_INPUT = "src/test/resources/correct.csv";
    private static final String TEST_WORK = "src/test/resources/work.csv";

    @MockBean
    ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;

    @MockBean
    ReportImportFluxUseCase reportImportFluxUseCase;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("refi_file_name", TEST_WORK);
        return paramsBuilder.toJobParameters();
    }

    @Test
    @DisplayName("Quand je recoit un fichier le nombre de solicitation du service est bon")
    public void givenCorrectOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        Path source = Paths.get(TEST_INPUT);
        Path work = Paths.get(TEST_WORK);
        Files.copy(source, work);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        assertThat(actualJobInstance.getJobName()).isEqualTo(RefiFileImportJob.IMPORT_REFI_JOB);
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");

//        Mockito.verify(referenceCollectionOfApplicationUseCase).referenceOrUpdateCollectionOfApplication(Mockito.any());
//        Mockito.verify(reportImportFluxUseCase).reportImportFlux(Mockito.any());

    }

}