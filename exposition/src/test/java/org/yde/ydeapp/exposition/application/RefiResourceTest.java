package org.yde.ydeapp.exposition.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.yde.ydeapp.application.in.application.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.flux.StoreFileRefiUseCase;
import org.yde.ydeapp.exposition.flux.RefiRessource;

@ExtendWith(SpringExtension.class)
@WebMvcTest({RefiRessource.class})
@DisplayName("Repository of refi file validation")
class RefiResourceTest {
    public static final int IGNORED = 37;
    public static final int REFERENCED = 27;
    public static final int UPDATED = 17;
    public static final int NOMOREUPDATED = 7;
    @MockBean
    private ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StoreFileRefiUseCase storeFileRefiUseCase;


    @Autowired
    private ObjectMapper objectMapper;



}
