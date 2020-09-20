package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.*;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RefiReader implements ItemReader<ApplicationSourcePosition>, JobExecutionListener {

    @Autowired
    StoreFileRefi storeFileRefi;

    private FlatFileItemReader<ApplicationSourcePosition> reader = null;
    private static final Logger log = LoggerFactory.getLogger(RefiReader.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private boolean isInit = false;


    @Override
    public ApplicationSourcePosition read() throws Exception {
        if (!isInit) {
            throw new UnexpectedInputException("Reader not initialized");
        }
        return reader.read();
    }

    private void initialiseNamesOfTokenizer(DelimitedLineTokenizer tokenizer) {
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
        reader = new FlatFileItemReader<>();
        final DefaultLineMapper<ApplicationSourcePosition> lineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        initialiseNamesOfTokenizer(tokenizer);
        lineMapper.setLineTokenizer(tokenizer);

        final BeanWrapperFieldSetMapper<ApplicationSourcePosition> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ApplicationSourcePosition.class);
        fieldSetMapper.setConversionService(createConversionService());
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setResource(new FileSystemResource(jobExecution.getJobParameters().getString(RepositoryOfRefiFileService.PROPERTY_FILE_NAME)));
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        reader.setEncoding("ISO-8859-1");
        reader.open(new ExecutionContext());
        isInit = true;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (isInit) {
            reader.close();
            isInit = false;
        }
    }
}
