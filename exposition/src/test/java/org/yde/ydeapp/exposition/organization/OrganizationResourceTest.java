package org.yde.ydeapp.exposition.organization;

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
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase.ReferenceOrganisationCmd;
import org.yde.ydeapp.domain.Organization;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({OrganizationResource.class})
class OrganizationResourceTest {

    public static final String ORGANIZATION_ONE = "Organization One";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReferenceOrganizationUseCase referenceOrganizationUseCase;
    private Organization organization;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        organization = new Organization(ORGANIZATION_ONE);
    }

    @Test
    @DisplayName("Reference a new organization")
    void testReferenceOrganization() throws Exception {
        // Given
        Mockito
            .when(referenceOrganizationUseCase.referenceOrganization(any()))
            .thenReturn(organization);

        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(ORGANIZATION_ONE, new ArrayList<>());

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.post("/api/organizations")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(referenceOrganisationCmd)))
            // Then
            .andExpect(status().is((HttpStatus.CREATED.value())));
    }

}