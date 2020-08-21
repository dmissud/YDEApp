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
import org.yde.ydeapp.application.in.CollectionApplicationCmd;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
        StatusFile statusFile=repositoryOfRefiFileService.storeRefiFile(multipartFile1);
        //Then
        Assertions.assertThat(statusFile).isEqualTo(StatusFile.FILEOK);
    }
    @Test
    @DisplayName("when the multipartfile is empty : store is ko")
    void when_the_multipartfile_is_empty_store_is_ko() throws Exception {
        //Given
        FileInputStream fisEmpty = new FileInputStream("src/test/resources/fileEmpty.csv");
        MockMultipartFile multipartFileEmpty = new MockMultipartFile("file", fisEmpty);

        ///When
        StatusFile statusFile=repositoryOfRefiFileService.storeRefiFile(multipartFileEmpty);
        //Then
        Assertions.assertThat(statusFile).isEqualTo(StatusFile.FILEKO);

    }


    @Test
    @DisplayName("when file refi contain commande is iterate")
    void when_file_refi_contain_commande_is_iterate() throws Exception{
        //Given
        FileInputStream fis = new FileInputStream("src/test/resources/creation.csv");
        MultipartFile multipartFile1 = new MockMultipartFile("file", fis);
        repositoryOfRefiFileService.storeRefiFile(multipartFile1);
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile1.getInputStream(), StandardCharsets.ISO_8859_1);

        //when
        CollectionApplicationCmd collectionApplicationCmd =repositoryOfRefiFileService.giveTransformerSourceToCmd();

        //Then

        Assertions.assertThat(collectionApplicationCmd.iterator().hasNext()).isEqualTo(true);

    }
    @Test
    @DisplayName("when file refi contain commande not iterate")
    void when_file_refi_contain_commande_not_iterate() throws Exception{
        //Given
        FileInputStream fis = new FileInputStream("src/test/resources/fileEmpty.csv");
        MultipartFile multipartFileEmpty = new MockMultipartFile("file", fis);
        repositoryOfRefiFileService.storeRefiFile(multipartFileEmpty);

        //when
        CollectionApplicationCmd collectionApplicationCmd=repositoryOfRefiFileService.giveTransformerSourceToCmd();

        //Then
       // Assertions.assertThat(transformerSourceToCmd.giveResult().getStatReadLineFile()).isEqualTo(0);
        Assertions.assertThat(collectionApplicationCmd.iterator().hasNext()).isEqualTo(false);

    }
}