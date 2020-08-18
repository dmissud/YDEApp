package org.yde.ydeapp.interfacerefi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest({RefiRessource.class})
public class RefiFileRessourceTest {
    //private static String targetName = "C:\\Users\\Stagiaire\\YDEApp\\interface\\src\\test\\refifile\\fileRefi.csv";
    String result = null;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    StoreFileRefi storeFileRefi;


    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    MockMultipartFile fileRefi = new MockMultipartFile("file", "refi.csv", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    @Test
    @DisplayName("upload file methode 1")
    public void should_upload_file_to_upload_path() throws Exception {

       // Given
    //    Mockito
        //        .when(storeFileRefi.storeRefiLight(fileRefi))
      //          .thenReturn(result = "ok");

//        mockMvc
//                //when
//                .perform(MockMvcRequestBuilders.post("/api/uploadRefiLight")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(fileRefi)))
//                //then
//                .andExpect(status().isOk());
        

    }
    @Test
    @DisplayName("test upload multipartfile")
    public void testUploadFile() throws Exception {

//     //   MockMultipartFile mockMultipartFile = new MockMultipartFile("file1", "refi.csv", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploadRefiLight").file(fileRefi).contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//        Assert.assertEquals(200, result.getResponse().getStatus());
//        Assert.assertNotNull(result.getResponse().getContentAsString());

    }



}
