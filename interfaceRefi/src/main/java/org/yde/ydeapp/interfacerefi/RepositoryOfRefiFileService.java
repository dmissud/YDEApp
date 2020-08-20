package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class RepositoryOfRefiFileService implements StoreFileRefi {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfRefiFileService.class);


    private String result = null;
    private MultipartFile refiFile = null;

    @Override
    public void storeRefiFile(MultipartFile refiFile) {
        this.refiFile = refiFile;
    }

    public TransformerSourceToCmd giveTransformerSourceToCmd() {

        try {
            return new TransformerSourceToCmd
                    (new InputStreamReader(refiFile.getInputStream(), StandardCharsets.ISO_8859_1));

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return null;

    }




//    @Override
//    public String storeRefiLight(MultipartFile refiFile) {
//
//        int compteur = 0;
//        CSVReader csvReader = null;
//
//        try {
//
//            HeaderColumnNameMappingStrategy<ApplicationSourceLight> strategy
//                    = new HeaderColumnNameMappingStrategy<>();
//            strategy.setType(ApplicationSourceLight.class);
//
//
//            CsvToBean<ApplicationSourceLight> csvToBean = new CsvToBeanBuilder<ApplicationSourceLight>(new InputStreamReader(refiFile.getInputStream(), "ISO_8859_1"))
//                    .withSeparator(';')
//                    .withMappingStrategy(strategy)
//                    .withType(ApplicationSourceLight.class)
//                    .withSkipLines(1)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
//
//
//            List<ApplicationSourceLight> csvIterator = csvToBean.parse();
//            List<ApplicationSourceLight> applicationSources = new ArrayList<>();
//
//            //while(csvIterator.hasNext()) {
//            for (ApplicationSourceLight applicationSourceLight : csvIterator) {
//                System.out.println("test parse csv");
//                // ApplicationSourceLight applicationSourceLight = csvIterator.next();
//                applicationSources.add(applicationSourceLight);
//                ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd =
//                        new ReferenceApplicationUseCase.ReferenceApplicationCmd(applicationSourceLight.getCodeApp(), applicationSourceLight.getShortDescription(),
//                                applicationSourceLight.getLongDescription(), applicationSourceLight.getIdResponsableMOE(), applicationSourceLight.getFirstNameResponsableMoe(),
//                                applicationSourceLight.getLastNameResponsableMoe());
//
//                //Application application = referenceApplicationUseCase.referenceOrUpdateApplication(referenceApplicationCmd);
//
//                if (application != null) {
//                    compteur++;
//                }
//            }
//
//
//        } catch (Exception ee) {
//            ee.printStackTrace();
//        }
//        result = "le nombre d'applications créés est de " + compteur;
//
//        return result;
//    }



}


