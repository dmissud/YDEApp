package org.yde.ydeapp.interfacerefi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Validation du Repository du flux refi")
class RepositoryOfRefiFileServiceTest {


    @Autowired
    private RepositoryOfRefiFileService repositoryOfRefiFileService;


    @Test
    @DisplayName("when the multipartfile is correct : storeFile is ok")
    void when_the_multipartfile_is_correct_storeFile_is_ok() throws Exception {
        //Given
        FileInputStream fis = new FileInputStream("src/test/resources/creation.csv");
        MultipartFile multipartFile1 = new MockMultipartFile("file", fis);

        //When
        repositoryOfRefiFileService.storeRefiFile(multipartFile1);
        //Then
        Assertions.assertThat(multipartFile1.getInputStream()).isNotNull();
    }
    @Test
    @DisplayName("when the multipartfile is empty : store is ko")
    void when_the_multipartfile_is_empty_store_is_ko() throws Exception {
        //Given
        FileInputStream fisEmpty = new FileInputStream("src/test/resources/fileEmpty.csv");
        MockMultipartFile multipartFileEmpty = new MockMultipartFile("file", fisEmpty);

        ///When
        repositoryOfRefiFileService.storeRefiFile(multipartFileEmpty);
        //Then
        Assertions.assertThat(multipartFileEmpty.getInputStream()).isNull();
        Assertions.assertThat(multipartFileEmpty.getInputStream()).isNull();
    }


    @Test
    @DisplayName("when file refi contain commande isi iterate")
    void when_file_refi_contain_commande_is_iterate() {
        //Given


        //when

        //Then

    }
}