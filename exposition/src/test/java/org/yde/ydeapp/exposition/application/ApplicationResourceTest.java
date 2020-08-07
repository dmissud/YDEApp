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
import org.yde.ydeapp.application.in.GetApplicationQuery;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.Application;
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
    public static final String A_SHORT_DESCRIPTION_UPDATE = "A update of short description";
    public static final String A_LONG_DESCRIPTION_UPDATE = "A update of long long description";
    public static final String UID_SECOND = "654321";
    public static final String FIRST_NAME_SECOND = "Alexander";
    public static final String LAST_NAME_SECOND = "TheGreat";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReferenceApplicationUseCase referenceApplicationUseCase;

    @MockBean
    GetApplicationQuery getApplicationQuery;

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
            .build();
        application_updated = new Application.Builder(CODE_APPLICATION)
            .withShortDescription(A_SHORT_DESCRIPTION_INIT)
            .withLongDescription(A_LONG_DESCRIPTION_INIT)
            .withResponsable(new Personne(UID_FIRST, FIRST_NAME_FIRST, LAST_NAME_FIRST))
            .build();
    }

    @Test
    @DisplayName("Reference a new application")
    void testReferenceApplication() throws Exception {
        // Given
        Mockito
            .when(referenceApplicationUseCase.referenceApplication(any()))
            .thenReturn(application);

        ApplicationDesc applicationDesc = buildASampleApplicationDescForUpdate();

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.post("/api/applications")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applicationDesc)))
            // Then
            .andExpect(status().is((HttpStatus.CREATED.value())));
    }

    @Test
    @DisplayName("Update a application")
    void testUpdateApplication() throws Exception {
        // Given
        Mockito
            .when(getApplicationQuery.getApplication(CODE_APPLICATION))
            .thenReturn(application);
        Mockito
            .when(referenceApplicationUseCase.updateApplication(any(String.class), any()))
            .thenReturn(application_updated);

        ApplicationDesc applicationDesc = buildASampleApplicationDescForCreate();

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.put("/api/applications/" + CODE_APPLICATION)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applicationDesc)))
            // Then
            .andExpect(status().is((HttpStatus.ACCEPTED.value())));
    }

    @Test
    @DisplayName("Consult a application")
    void testRetrieveApplicationByCodeApplication() throws Exception {
        // Given
        Mockito
            .when(getApplicationQuery.getApplication(CODE_APPLICATION))
            .thenReturn(application);

        ApplicationDesc applicationDesc = buildASampleApplicationDescForCreate();

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.get("/api/applications/" + CODE_APPLICATION)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Consult all applications")
    void testRetrieveAllApplication() throws Exception {
        // Given
        Mockito
            .when(getApplicationQuery.getApplication(CODE_APPLICATION))
            .thenReturn(application);

        ApplicationDesc applicationDesc = buildASampleApplicationDescForCreate();

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.get("/api/applications/")
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isOk());
    }

    private ApplicationDesc buildASampleApplicationDescForCreate() {
        ApplicationDesc applicationDesc = new ApplicationDesc();
        applicationDesc.setCodeApplication(CODE_APPLICATION);
        applicationDesc.setShortDescription(A_SHORT_DESCRIPTION_INIT);
        applicationDesc.setLongDescription(A_LONG_DESCRIPTION_INIT);
        applicationDesc.setUid(UID_FIRST);
        applicationDesc.setFirstName(FIRST_NAME_FIRST);
        applicationDesc.setLastName(LAST_NAME_FIRST);
        return applicationDesc;
    }

    private ApplicationDesc buildASampleApplicationDescForUpdate() {
        ApplicationDesc applicationDesc = new ApplicationDesc();
        applicationDesc.setCodeApplication(CODE_APPLICATION);
        applicationDesc.setShortDescription(A_SHORT_DESCRIPTION_UPDATE);
        applicationDesc.setLongDescription(A_LONG_DESCRIPTION_UPDATE);
        applicationDesc.setUid(UID_SECOND);
        applicationDesc.setFirstName(FIRST_NAME_SECOND);
        applicationDesc.setLastName(LAST_NAME_SECOND);
        return applicationDesc;
    }
}