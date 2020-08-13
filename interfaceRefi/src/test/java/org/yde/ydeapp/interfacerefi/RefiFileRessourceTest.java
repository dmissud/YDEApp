package org.yde.ydeapp.interfacerefi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest({RefiRessource.class})
public class RefiFileRessourceTest {
    //private static String fileName = "C:\\Users\\Stagiaire\\YDEApp\\exposition\\src\\test\\refifile\\fileRefi.csv";
//    private static String fileName = "\\YDEApp\\exposition\\src\\test\\refifile\\fileRefi.csv";
//    final String completeFileName= getResourcePath(fileName);
//    File fileRefi = new File(completeFileName) ;
//
//
//    public static String getResourcePath(String fileName) {
//        final File f = new File("");
//        final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
//        return dossierPath;
//    }

    @Autowired
    private MockMvc mockMvc;




    @MockBean
    RepositoryOfRefiImpl repositoryOfRefi;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Test
    @DisplayName("enregistrement du fichier et le parse du fichier")
    public void should_upload_image_to_upload_path() throws Exception {
        MockMultipartFile fileRefi = new MockMultipartFile("file", "refi.csv", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        mockMvc
                //when
                .perform(MockMvcRequestBuilders.post("/api/UploadFile")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fileRefi)))
                //then
                .andExpect(status().isOk());

    }

}
