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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yde.ydeapp.application.in.ApplicationQuery;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.application.*;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.time.LocalDate;

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
    private static final String STATE = "Active";
    private static final LocalDate DATE_OF_CREATION = LocalDate.of(2020, 1, 1);
    private static final LocalDate DATE_OF_LAST_UPDATE = LocalDate.of(2020, 1, 1);
    private static final LocalDate DATE_END_IN_REALITY = LocalDate.of(2020, 1, 1);
    public static final String TYPE_OF_SOLUTION = "10000000";
    public static final String NAME_OF_FIRMWARE = "NameOfFirmware";
    private static final String LABEL_OF_SOURCING = "IBM";
    private static final String PRIVILEGE_INFORMATION ="NON";
    private static final String PERSONAL_DATA ="OUI";
    private static final String SERVICE_CLASS ="Service minimum";
    private static final String AVAILABILITY ="C12";
    private static final String RPO ="01 J 01 H 12 MIN";
    private static final String RTO ="02 J 03 H 22 MIN";


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
            .withCycleLife(new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY))
            .withItSolution(new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING))
            .withCriticity(new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO))
            .build();
        application_updated = new Application.Builder(CODE_APPLICATION)
            .withShortDescription(A_SHORT_DESCRIPTION_UPDATE)
            .withLongDescription(A_LONG_DESCRIPTION_UPDATE)
            .withResponsable(new Personne(UID_SECOND, FIRST_NAME_SECOND, LAST_NAME_SECOND))
            .withOrganization(new OrganizationIdent(ID_REFOG_MOE_SECOND, NAME_OF_ORGA_MOE))
            .withCycleLife(new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY))
            .withItSolution(new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING))
            .withCriticity(new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO))
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