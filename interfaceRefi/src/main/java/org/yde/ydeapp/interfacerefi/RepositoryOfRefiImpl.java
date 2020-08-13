package org.yde.ydeapp.interfacerefi;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.Application;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class RepositoryOfRefiImpl implements StoreFileRefi {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfRefiImpl.class);

    //private MultipartFile refiFile=null;

    @Autowired
    ReferenceApplicationUseCase referenceApplicationUseCase;

    @Override
    public String storeRefiFile(MultipartFile refiFile) {

       String result = null;
        CSVReader csvReader=null;

        try
        {
            /**
             * Reading the CSV File
             * Delimiter is comma
             * Start reading from line 1
             */
           InputStream is = refiFile.getInputStream();
/*
            csvReader = new CSVReaderBuilder(new InputStreamReader(refiFile.getInputStream()))

                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();


            //employeeDetails stores the values current line
            String[] applicationDetails = null;
            //Create List for holding Employee objects
           // List<ApplicationSourceEntity> applicationList = new ArrayList<>();

           // while((applicationDetails = csvReader.readNext())!=null)
            applicationDetails = csvReader.readNext();
            if (applicationDetails!=null){

                //Save the employee details in Employee object
                ApplicationSource emp = new ApplicationSource(applicationDetails[0],
                        applicationDetails[1],applicationDetails[2],
                        applicationDetails[3],applicationDetails[4],
                        applicationDetails[5],applicationDetails[6],
                        applicationDetails[7],applicationDetails[8],
                        applicationDetails[9],applicationDetails[10],
                        applicationDetails[11],applicationDetails[12],
                        applicationDetails[13],applicationDetails[14],
                        applicationDetails[15],applicationDetails[16],
                        applicationDetails[17],applicationDetails[18],
                        applicationDetails[19],applicationDetails[20],
                        applicationDetails[21],applicationDetails[22],
                        applicationDetails[23],applicationDetails[24],
                        applicationDetails[25],applicationDetails[26],
                        applicationDetails[27],applicationDetails[28],
                        applicationDetails[29],applicationDetails[30],
                        applicationDetails[31],applicationDetails[32],
                        applicationDetails[33],applicationDetails[34],
                        applicationDetails[35],applicationDetails[36],
                        applicationDetails[37],applicationDetails[38],
                        applicationDetails[39],applicationDetails[40],
                        applicationDetails[41],applicationDetails[42],
                        applicationDetails[43],applicationDetails[44],
                        applicationDetails[45],applicationDetails[46],
                        applicationDetails[47],applicationDetails[48],
                        applicationDetails[49],applicationDetails[50],
                        applicationDetails[51],applicationDetails[52],
                        applicationDetails[53],applicationDetails[54],
                        applicationDetails[55],applicationDetails[56],
                        applicationDetails[57]);
                */

                CsvToBean<ApplicationSource> csvToBean= new CsvToBeanBuilder(new InputStreamReader(refiFile.getInputStream()))
                        .withSeparator(';')
                        .withType(ApplicationSource.class)
                        .build();


                Iterator<ApplicationSource> csvIterator = csvToBean.iterator();
                List<ApplicationSource> applicationSources = new ArrayList<>();

                while(csvIterator.hasNext()) {

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

}