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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yde.ydeapp.application.in.application.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.flux.RefiImportQuery;
import org.yde.ydeapp.application.in.flux.StoreFileRefiUseCase;
import org.yde.ydeapp.application.in.user.GetUserQuery;
import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.organization.Organization;
import org.yde.ydeapp.exposition.flux.RefiRessource;
import org.yde.ydeapp.exposition.security.jwt.JwtAuthenticationEntryPoint;
import org.yde.ydeapp.exposition.security.jwt.JwtTokenManager;
import org.yde.ydeapp.exposition.security.jwt.YdeAppUserDetailsService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({RefiRessource.class, YdeAppUserDetailsService.class})
@DisplayName("Repository of refi file validation")
class RefiResourceTest {
    public static final int IGNORED = 37;
    public static final int REFERENCED = 27;
    public static final int UPDATED = 17;
    public static final int NOMOREUPDATED = 7;
    private static final String ID_FLUX = "1";
    @MockBean
    private ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StoreFileRefiUseCase storeFileRefiUseCase;

    @MockBean
    RefiImportQuery refiImportQuery;

    @MockBean
    private GetUserQuery getUserQuery;

    @MockBean
    JwtTokenManager jwtTokenManager;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private ObjectMapper objectMapper;

    private ImportFlux importFlux;
    @BeforeEach
    void setup() {
        importFlux = new ImportFlux("Test");
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Retrieve a organization")
    void when_i_retrieve_the_detail_of_a_organization_http_status_is_ok() throws Exception {
        // Given
        Mockito
            .when(refiImportQuery.getImportFlux(Long.valueOf(ID_FLUX)))
            .thenReturn(importFlux);

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.get("/api/V1/uploadBatchRefi/" + ID_FLUX)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    @DisplayName("Try retrieve a organization with no grants and get forbidden")
    void when_i_retrieve_the_detail_with_no_grants_of_a_organization_http_status_is_ko() throws Exception {
        // Given
        Mockito
            .when(refiImportQuery.getImportFlux(Long.valueOf(ID_FLUX)))
            .thenReturn(importFlux);

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.get("/api/V1/uploadBatchRefi/" + ID_FLUX)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isForbidden());
    }
}
