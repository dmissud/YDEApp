package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RefiReaderBuilder {

    private static final Logger log = LoggerFactory.getLogger(RefiReaderBuilder.class);

    public FlatFileItemReader<ApplicationSourcePosition> build() {
        FlatFileItemReader<ApplicationSourcePosition> reader = new FlatFileItemReader<>();
        reader.setLineMapper(buildLineMapperForRefiFile());
        reader.setLinesToSkip(1);
        reader.setEncoding("ISO-8859-1");
        log.trace("RefiReaderBuilder as Build a new Reader");
        return reader;
    }

    private DefaultLineMapper<ApplicationSourcePosition> buildLineMapperForRefiFile() {
        final DefaultLineMapper<ApplicationSourcePosition> lineMapper = initLineMapperWithDelimitedTokenizer();
        makeLineMapperWorkWithApplicationBean(lineMapper);
        return lineMapper;
    }

    private DefaultLineMapper<ApplicationSourcePosition> initLineMapperWithDelimitedTokenizer() {
        final DefaultLineMapper<ApplicationSourcePosition> lineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(";");
        tokenizer.setStrict(false);
        initialiseNamesForMappingLineToBean(tokenizer);
        lineMapper.setLineTokenizer(tokenizer);
        return lineMapper;
    }

    private void makeLineMapperWorkWithApplicationBean(DefaultLineMapper<ApplicationSourcePosition> lineMapper) {
        final BeanWrapperFieldSetMapper<ApplicationSourcePosition> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ApplicationSourcePosition.class);
        fieldSetMapper.setConversionService(createConversionService());
        lineMapper.setFieldSetMapper(fieldSetMapper);
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

    private ConversionService createConversionService() {
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
                    return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
        });
        return conversionService;
    }
}
