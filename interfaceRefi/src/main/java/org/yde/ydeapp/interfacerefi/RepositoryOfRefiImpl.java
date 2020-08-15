package org.yde.ydeapp.interfacerefi;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.Application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class RepositoryOfRefiImpl implements StoreFileRefi {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfRefiImpl.class);

    //private MultipartFile refiFile=null;

    @Autowired
    ReferenceApplicationUseCase referenceApplicationUseCase;
    String result = null;

    @Override
    public String storeRefiFile(MultipartFile refiFile) {

        CSVReader csvReader=null;

        try
        {

            InputStream is = refiFile.getInputStream();
            HeaderColumnNameMappingStrategy<ApplicationSource> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ApplicationSource.class);



            CsvToBean<ApplicationSource> csvToBean= new CsvToBeanBuilder<ApplicationSource>(new InputStreamReader(refiFile.getInputStream(),"ISO_8859_1"))
                    .withSeparator(';')
                    .withMappingStrategy(strategy)
                    .withType(ApplicationSource.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            Iterator<ApplicationSource> csvIterator = csvToBean.iterator();
            List<ApplicationSource> applicationSources = new ArrayList<>();

            while(csvIterator.hasNext()) {
                System.out.println("test parse csv");
                ApplicationSource applicationSource = csvIterator.next();
                applicationSources.add(applicationSource);
                ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd =
                        new ReferenceApplicationUseCase.ReferenceApplicationCmd(applicationSource.getCodeApp(), applicationSource.getShortDescription(),
                                applicationSource.getLongDescription(), applicationSource.getIdResponsableMOE(), applicationSource.getFirstNameResponsableMoe(),
                                applicationSource.getLastNameResponsableMoe());

                Application application = referenceApplicationUseCase.referenceApplication(referenceApplicationCmd);
            }


        }

        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        return result;
    }

    @Override
    public String storeRefiLight(MultipartFile refiFile) {

        int compteur = 0;
        CSVReader csvReader=null;

        try
        {

            HeaderColumnNameMappingStrategy<ApplicationSourceLight> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ApplicationSourceLight.class);




            CsvToBean<ApplicationSourceLight> csvToBean= new CsvToBeanBuilder<ApplicationSourceLight>(new InputStreamReader(refiFile.getInputStream(),"ISO_8859_1"))
                    .withSeparator(';')
                    .withMappingStrategy(strategy)
                  //  .withType(ApplicationSourceLight.class)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();






            List <ApplicationSourceLight> csvIterator = csvToBean.parse();
            List<ApplicationSourceLight> applicationSources = new ArrayList<>();

            //while(csvIterator.hasNext()) {
            for(ApplicationSourceLight applicationSourceLight: csvIterator)    {
                System.out.println("test parse csv");
               // ApplicationSourceLight applicationSourceLight = csvIterator.next();
                applicationSources.add(applicationSourceLight);
                ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd =
                        new ReferenceApplicationUseCase.ReferenceApplicationCmd(applicationSourceLight.getCodeApp(), applicationSourceLight.getShortDescription(),
                                applicationSourceLight.getLongDescription(), applicationSourceLight.getIdResponsableMOE(), applicationSourceLight.getFirstNameResponsableMoe(),
                                applicationSourceLight.getLastNameResponsableMoe());

                Application application = referenceApplicationUseCase.referenceApplication(referenceApplicationCmd);

                if (application !=null){
                    compteur ++;
                }
            }


        }

        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        result = "le nombre d'applications créés est de " + compteur;

        return result;
    }

}


