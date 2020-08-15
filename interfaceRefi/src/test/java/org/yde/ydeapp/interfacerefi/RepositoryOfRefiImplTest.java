package org.yde.ydeapp.interfacerefi;



import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.Assert.assertEquals;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;


import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RepositoryOfRefiImplTest {

    @Autowired
    RepositoryOfRefiImpl repositoryOfRefiimpl;

    public RepositoryOfRefiImplTest() throws IOException {
    }


    @Test
    @DisplayName("read file with 7 columns")
    void create_the_file_in_the_infratructure() throws IOException {

        //Given
        MultipartFile multipartFile1 = new MockMultipartFile("test.csv","test.csv","text/csv",
                new FileInputStream(new File("src/test/resources/fileRefiLight.txt")));


        //When
        final String result = repositoryOfRefiimpl.storeRefiLight(multipartFile1);
        //then
        assertEquals ("le nombre d'applications créés est de 1",result);

    }








     


}
