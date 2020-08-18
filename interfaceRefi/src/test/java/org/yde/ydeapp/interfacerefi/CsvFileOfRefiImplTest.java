package org.yde.ydeapp.interfacerefi;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.Assert.assertEquals;

import java.io.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CsvFileOfRefiImplTest {

    @Autowired
    CsvFileOfRefiImpl csvFileOfRefiimpl;

    public CsvFileOfRefiImplTest() throws IOException {
    }


    @Test
    @DisplayName("read file with 7 columns")
    void create_the_file_in_the_infratructure() throws IOException {

        //Given
        MultipartFile multipartFile1 = new MockMultipartFile("test.csv","test.csv","text/csv",
                new FileInputStream(new File("src/test/resources/fileRefiLight.txt")));


        //When
       // final String result = csvFileOfRefiimpl.storeRefiLight(multipartFile1);
        //then
        //assertEquals ("le nombre d'applications créés est de 1",result);

    }








     


}
