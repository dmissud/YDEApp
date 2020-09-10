package org.yde.ydeapp.exposition.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yde.ydeapp.application.in.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.ResultOfCollection;
import org.yde.ydeapp.interfacerefi.StoreFileRefi;
import org.yde.ydeapp.interfacerefi.TransformerSourceToCmd;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({RefiRessource.class})
@DisplayName("Repository of refi file validation")
public class RefiResourceTest {
    @MockBean
    private ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StoreFileRefi storeFileRefi;


    @Autowired
    private ObjectMapper objectMapper;

    private ResultOfCollection resultOfCollection;

    @BeforeEach
    void setup() {
       resultOfCollection =new ResultOfCollection();
       resultOfCollection.setIgnoreCounter(37);
       resultOfCollection.setReferenceCounter(27);
       resultOfCollection.setUpdateCounter(17);
       resultOfCollection.setNoMoreUpdated(7);
    }

    @Test
    @DisplayName("Store the file in the repository ")
    void store_the_file_in_the_repository() throws Exception {

        //Given
        FileInputStream fis = new FileInputStream("src/test/resources/creation.csv");
        MockMultipartFile multipartFile1 = new MockMultipartFile("file", fis);
       // MockMultipartFile multipartFile1 = new MockMultipartFile("creation.csv", new FileInputStream(new File("src/test/resources/creation.csv")));
        CollectionApplicationCmd collectionApplicationCmd = new TransformerSourceToCmd
                (new InputStreamReader(multipartFile1.getInputStream(), StandardCharsets.ISO_8859_1));
        Mockito
                .when(storeFileRefi.giveTransformerSourceToCmd())
                .thenReturn(collectionApplicationCmd);
        Mockito
                .when(referenceCollectionOfApplicationUseCase.referenceOrUpdateCollectionOfApplication(collectionApplicationCmd))
                .thenReturn(resultOfCollection);

        //When
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/V1/uploadRefi")
                .file(multipartFile1)
                .param("some-random", "4"))

                //then
                .andExpect(status().is(HttpStatus.OK.value()));

                //.andExpect(content().string("success"));



    }








     


}
