package org.yde.ydeapp.interfacerefi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.service.ApplicationManagementService;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class TransformerSourceToCmdTest {


    @Test
    @DisplayName("when_the_file_containt_nine_commande_they_read_nine_line")
    void when_the_file_containt_nine_commande_they_read_nine_line() throws Exception {
        //given

        FileInputStream fis = new FileInputStream("src/test/resources/creation.csv");
        MultipartFile multipartFile1 = new MockMultipartFile("file", fis);

        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile1.getInputStream(), StandardCharsets.ISO_8859_1);


        TransformerSourceToCmd transformerSourceToCmd=new TransformerSourceToCmd(inputStreamReader);

        for (ReferenceApplicationUseCase.ReferenceApplicationCmd cmd : transformerSourceToCmd) {
            cmd.validate();
        }

        //when

        StatTraitementRefiFile statTraitementRefiFile = transformerSourceToCmd.giveResult();

        //then
        Assertions.assertThat(statTraitementRefiFile.getStatReadLineFile()).isEqualTo(9);

    }

    @Test
    @DisplayName( "when iterate commande with application desactivé rejeted line is equal 1 " )
    void when_iterate_commande_with_application_desactive_rejeted_line_is_equal_1() throws Exception {
       //Given
        FileInputStream fis = new FileInputStream("src/test/resources/desactive.csv");
        MultipartFile multipartFile1 = new MockMultipartFile("file", fis);

        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile1.getInputStream(), StandardCharsets.ISO_8859_1);
        TransformerSourceToCmd transformerSourceToCmd =new TransformerSourceToCmd(inputStreamReader);
        //when
        for (ReferenceApplicationUseCase.ReferenceApplicationCmd cmd : transformerSourceToCmd) {
            cmd.validate();
        }
        //then
        Assertions.assertThat(transformerSourceToCmd.giveResult().getStatReadLineFile()).isEqualTo(1);
        Assertions.assertThat(transformerSourceToCmd.giveResult().getStatRejetedLinefile()).isEqualTo(1);

    }
    @Test
    @DisplayName( "when iterate commande with application CodeOfTypeOfApplication equald to SU rejeted line is equal 1 " )
    void when_iterate_commande_with_application_CodeOfTypeOfApplication_equals_to_SU_rejeted_line_is_equal_1() throws Exception {
        //Given
        FileInputStream fis = new FileInputStream("src/test/resources/SU.csv");
        MultipartFile multipartFile1 = new MockMultipartFile("file", fis);

        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile1.getInputStream(), StandardCharsets.ISO_8859_1);
        TransformerSourceToCmd transformerSourceToCmd =new TransformerSourceToCmd(inputStreamReader);

        //when
        for (ReferenceApplicationUseCase.ReferenceApplicationCmd cmd : transformerSourceToCmd) {
            cmd.validate();
        }

        //then
        Assertions.assertThat(transformerSourceToCmd.giveResult().getStatReadLineFile()).isEqualTo(1);
        Assertions.assertThat(transformerSourceToCmd.giveResult().getStatRejetedLinefile()).isEqualTo(1);
    }
    @Test
    @DisplayName( "when iterate commande with application correct " )
    void when_iterate_commande_with_application_read_line_is_equal_1() throws Exception {
        //Given
        FileInputStream fis = new FileInputStream("src/test/resources/correct.csv");
        MultipartFile multipartFile1 = new MockMultipartFile("file", fis);

        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile1.getInputStream(), StandardCharsets.ISO_8859_1);
        TransformerSourceToCmd transformerSourceToCmd =new TransformerSourceToCmd(inputStreamReader);
        //when
        for (ReferenceApplicationUseCase.ReferenceApplicationCmd cmd : transformerSourceToCmd) {
            cmd.validate();
        }
        //then
        Assertions.assertThat(transformerSourceToCmd.giveResult().getStatReadLineFile()).isEqualTo(1);
        Assertions.assertThat(transformerSourceToCmd.giveResult().getStatRejetedLinefile()).isZero();

    }
}