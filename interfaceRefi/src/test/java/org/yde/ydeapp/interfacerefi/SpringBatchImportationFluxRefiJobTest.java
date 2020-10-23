package org.yde.ydeapp.interfacerefi;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.yde.ydeapp.application.in.application.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.flux.ReportImportFluxUseCase;
import org.yde.ydeapp.application.in.organization.OrganizationQuery;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {RefiFileImportJob.class, RefiJobListener.class, YdeAppWriter.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Validation du Repository du flux refi")
@MockBean(OrganizationQuery.class)
@MockBean(ReferenceCollectionOfApplicationUseCase.class)
@MockBean(ReportImportFluxUseCase.class)
class SpringBatchImportationFluxRefiJobTest {

    private static final String TEST_INPUT = "src/test/resources/correct.csv";
    private static final String TEST_WORK = "src/test/resources/work.csv";

    @Autowired
    private ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;

    @Autowired
    private ReportImportFluxUseCase reportImportFluxUseCase;

    @Autowired
    private OrganizationQuery organizationQuery;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
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
        List<OrganizationIdent> lstOrganizationIdent = new ArrayList<>();
        lstOrganizationIdent.add(new OrganizationIdent("10001000", "CPL03 LIST MANAGEMENT AND TRANSACTION FILTERING"));

        Mockito
            .doReturn(lstOrganizationIdent)
            .when(organizationQuery)
            .getOrganizations();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        assertThat(actualJobInstance.getJobName()).isEqualTo(RefiFileImportJob.IMPORT_REFI_JOB);
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");

        Mockito.verify(referenceCollectionOfApplicationUseCase).referenceOrUpdateCollectionOfApplication(Mockito.any());
        Mockito.verify(reportImportFluxUseCase).reportImportFlux(Mockito.any());

    }

}
