package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.domain.out.BusinessException;
import org.yde.ydeapp.infrastructure.fluxrefi.ReposiroryFluxRefiConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class RefiReader implements ItemReader<ApplicationSourcePosition>, JobExecutionListener {

    private final ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration;

    @Autowired
    public RefiReader(ReposiroryFluxRefiConfiguration reposiroryFluxRefiConfiguration) {
        this.reposiroryFluxRefiConfiguration = reposiroryFluxRefiConfiguration;
    }

    private FlatFileItemReader<ApplicationSourcePosition> reader = null;
    private static final Logger log = LoggerFactory.getLogger(RefiReader.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private boolean isInit = false;
    private String nameOfFile;
    private Path targetLocation;


    @Override
    public ApplicationSourcePosition read() throws Exception {
        if (!isInit) {
            throw new UnexpectedInputException("Reader not initialized");
        }
        return reader.read();
    }

    private void initialiseNamesForMappingLineToBean(DelimitedLineTokenizer tokenizer) {
        tokenizer.setNames(
            "identifiant",
            "codeApp",
            "shortLibelle",
            "longLibelle",
            "description",
            "codeOfTypeOfApplication",
            "libelleTypeApplication",
            "state",
            "dateOfCreation",
            "dateOfLastUpdate",
            "dateEndProjected",
            "applicationTarget",
            "dateEndInReality",
            "dateBeginningExploitation",
            "dateEndExploitation",
            "idRefogEntityOwner",
            "codeEntityOwner",
            "labelEntityOwner",
            "idRefogEntityMOA",
            "codeEntityMOA",
            "labelEntityMOA",
            "idResponsableMOE",
            "lastNameResponsableMoe",
            "firstNameResponsableMoe",
            "codeEntityResponsableMoe",
            "labelEntityResponsableMoe",
            "idRefogEntityMoe",
            "codeEntityMoe",
            "labelEntityMoe",
            "idRefogEntityProduction",
            "codeEntityProduction",
            "labelEntityProduction",
            "codeAttachmentUnderSector",
            "labelAttachmentUnderSector",
            "codeAttachmentSector",
            "labelAttachmentSector",
            "codeAttachmentPatrimony",
            "labelAttachmentPatrimony",
            "codeAttachmentDPP",
            "labelAttachmentDPP",
            "labelFunctionalCluster",
            "labelOfSourcingMode",
            "privilegeInformation",
            "personalData",
            "serviceClass",
            "availability",
            "RPO",
            "RTO",
            "integrity",
            "privacy",
            "traceability",
            "businessCriticality",
            "continuityLevel",
            "derogation",
            "dateOfValidationOfServiceDefinition",
            "typeOfSolution",
            "nameOfFirmware",
            "technicalApplication"
        );
    }

    public ConversionService createConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(conversionService);
        // Don't change to Lamba, Spring doesn't find type
        // Cf : https://stackoverflow.com/questions/25711858/spring-cant-determine-generic-types-when-lambda-expression-is-used-instead-of-a
        conversionService.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String text) {
                if (text.isEmpty()) {
                    return null;
                } else {
                    return LocalDate.parse(text, formatter);
                }
            }
        });
        return conversionService;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        findFileNameFromJobParameter(jobExecution);
        moveFileInWorkPlace();
        initReaderForRefiFile(buildLineMapperForRefiFile());

        isInit = true;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (isInit) {
            reader.close();
            try {
                Files.delete(Paths.get(this.targetLocation.toString()));
            } catch (IOException e) {
                throw new BusinessException("File management is broked : "+e.getMessage());
            }
            isInit = false;
        }
    }
    private void moveFileInWorkPlace() {
        Path fileUploadLocation = Paths.get(reposiroryFluxRefiConfiguration.getUploadDir()).toAbsolutePath().normalize();
        Path fileWorkLocation = Paths.get(reposiroryFluxRefiConfiguration.getWorkDir()).toAbsolutePath().normalize();
        targetLocation = fileWorkLocation.resolve(this.nameOfFile);
        Path sourceLocation = fileUploadLocation.resolve(this.nameOfFile);
        try {
            Files.move(sourceLocation, targetLocation);
        } catch (IOException e) {
            throw new BusinessException("File management is broked : "+e.getMessage());
        }
    }

    private void findFileNameFromJobParameter(JobExecution jobExecution) {
        Path pathNameOfFile = Paths.get(Objects.requireNonNull(jobExecution.getJobParameters().getString(ReposiroryFluxRefiConfiguration.PROPERTY_FILE_NAME)));
        this.nameOfFile = pathNameOfFile.getFileName().toString();
        log.debug("File : {}", this.nameOfFile);
    }

    private DefaultLineMapper<ApplicationSourcePosition> buildLineMapperForRefiFile() {
        final DefaultLineMapper<ApplicationSourcePosition> lineMapper = initLineMapperWithDelimitedTokenizer();
        makeLineMapperWorkWithApplicationBean(lineMapper);
        return lineMapper;
    }

    private void initReaderForRefiFile(DefaultLineMapper<ApplicationSourcePosition> lineMapper) {
        reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(targetLocation.toString()));
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        reader.setEncoding("ISO-8859-1");
        reader.open(new ExecutionContext());
    }

    private void makeLineMapperWorkWithApplicationBean(DefaultLineMapper<ApplicationSourcePosition> lineMapper) {
        final BeanWrapperFieldSetMapper<ApplicationSourcePosition> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ApplicationSourcePosition.class);
        fieldSetMapper.setConversionService(createConversionService());
        lineMapper.setFieldSetMapper(fieldSetMapper);
    }

    private DefaultLineMapper<ApplicationSourcePosition> initLineMapperWithDelimitedTokenizer() {
        final DefaultLineMapper<ApplicationSourcePosition> lineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(";");
        initialiseNamesForMappingLineToBean(tokenizer);
        lineMapper.setLineTokenizer(tokenizer);
        return lineMapper;
    }

}
