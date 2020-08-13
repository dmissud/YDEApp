package org.yde.ydeapp.interfacerefi;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RepositoryOfRefiImplTest {

    @Autowired
    RepositoryOfRefiImpl repositoryOfRefiimpl;


    @Test
    @DisplayName("file in infrastructure")
    void create_the_file_in_the_infratructure(){

        //Given
        MultipartFile fileRefi = new MockMultipartFile("fileRefi", "fileRefi.csv", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        System.out.println("test");
        //When
        repositoryOfRefiimpl.storeRefiFile(fileRefi);
        //then
        Assertions.assertThat(!fileRefi.isEmpty());

    }


}
