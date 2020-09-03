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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yde.ydeapp.application.in.ApplicationQuery;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.OrganizationIdent;
import org.yde.ydeapp.domain.Personne;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({ApplicationResource.class})
class ApplicationResourceTest {

    public static final String CODE_APPLICATION = "AP00001";
    public static final String A_SHORT_DESCRIPTION_INIT = "A short description";
    public static final String A_LONG_DESCRIPTION_INIT = "A long long description";
    public static final String UID_FIRST = "123456";
    public static final String FIRST_NAME_FIRST = "Jhon";
    public static final String LAST_NAME_FIRST = "Doe";
    public static final String ID_REFOG_MOE_FIRST = "10000000";
    public static final String A_SHORT_DESCRIPTION_UPDATE = "A update of short description";
    public static final String A_LONG_DESCRIPTION_UPDATE = "A update of long long description";
    public static final String UID_SECOND = "654321";
    public static final String FIRST_NAME_SECOND = "Alexander";
    public static final String LAST_NAME_SECOND = "TheGreat";
    public static final String ID_REFOG_MOE_SECOND = "10000000";
    public static final String NAME_OF_ORGA_MOE = "NameOfOrgaMOE";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReferenceApplicationUseCase referenceApplicationUseCase;

    @MockBean
    private ApplicationQuery applicationQuery;

    @Autowired
    private ObjectMapper objectMapper;


    private Application application;
    private Application application_updated;

    @BeforeEach
    void setup() {
        application = new Application.Builder(CODE_APPLICATION)
                .withShortDescription(A_SHORT_DESCRIPTION_INIT)
                .withLongDescription(A_LONG_DESCRIPTION_INIT)
                .withResponsable(new Personne(UID_FIRST, FIRST_NAME_FIRST, LAST_NAME_FIRST))
                .withOrganization(new OrganizationIdent(ID_REFOG_MOE_FIRST, NAME_OF_ORGA_MOE))
                .build();
        application_updated = new Application.Builder(CODE_APPLICATION)
                .withShortDescription(A_SHORT_DESCRIPTION_INIT)
                .withLongDescription(A_LONG_DESCRIPTION_INIT)
                .withResponsable(new Personne(UID_FIRST, FIRST_NAME_FIRST, LAST_NAME_FIRST))
                .withOrganization(new OrganizationIdent(ID_REFOG_MOE_FIRST, NAME_OF_ORGA_MOE))
                .build();
    }

    @Test
    @DisplayName("Consult a application")
    void testRetrieveApplicationByCodeApplication() throws Exception {
        // Given
        Mockito
                .when(applicationQuery.getApplication(CODE_APPLICATION))
                .thenReturn(application);

        mockMvc
                // When
                .perform(MockMvcRequestBuilders.get("/api/V1/applications/" + CODE_APPLICATION)
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Consult all applications")
    void testRetrieveAllApplication() throws Exception {
        // Given
        Mockito
                .when(applicationQuery.getApplication(CODE_APPLICATION))
                .thenReturn(application);

        mockMvc
                // When
                .perform(MockMvcRequestBuilders.get("/api/V1/applications/")
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk());
    }

}