package org.yde.ydeapp.exposition.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.ResultOfCollection;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({RefiRessource.class})
public class CsvFileOfRefiImplTest {
    @MockBean
    private ReferenceApplicationUseCase referenceApplicationUseCase;

    @Autowired
    MockMvc mockMvc;




    @Autowired
    private ObjectMapper objectMapper;

    private ResultOfCollection resultOfCollection;

    @BeforeEach
    void setup() {
       resultOfCollection =new ResultOfCollection();
       resultOfCollection.setIgnoreCounter(37);
       resultOfCollection.setReferenceCounter(27);
       resultOfCollection.setUpdateCounter(17);
    }

    @Test
    @DisplayName("read file ")
    void create_the_file_in_the_infratructure() throws Exception {

        //Given
        MockMultipartFile multipartFile1 = new MockMultipartFile("epita.csv","epita.csv","text/csv",
                new FileInputStream(new File("src/test/resources/epita.csv")));


        //When
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploadRefi")
                .file(multipartFile1)
                .param("some-random", "4"))

                //then
                .andExpect(status().is(200));

                //.andExpect(content().string("success"));



    }








     


}
