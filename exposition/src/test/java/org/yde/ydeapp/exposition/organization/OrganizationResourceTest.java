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
import org.yde.ydeapp.application.in.OrganizationQuery;
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
    public static final String ORGANIZATION_IDREFOG_ONE = "10000000";
    public static final String ORGANIZATION_TWO = "Organization Two";
    public static final String ORGANIZATION_IDREFOG_TWO = "10000001";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReferenceOrganizationUseCase referenceOrganizationUseCase;

    @MockBean
    private OrganizationQuery organizationQuery;

    @Autowired
    private ObjectMapper objectMapper;

    private Organization organization;


    @BeforeEach
    void setup() {
        organization = new Organization(ORGANIZATION_IDREFOG_ONE, ORGANIZATION_ONE);
    }

    @Test
    @DisplayName("Reference a new organization")
    void when_i_reference_a_new_organization_http_status_is_created() throws Exception {
        // Given

        ArrayList<ReferenceOrganisationCmd> lst = new ArrayList<>();
        lst.add(new ReferenceOrganisationCmd(ORGANIZATION_TWO, ORGANIZATION_IDREFOG_TWO, new ArrayList<>()));
        ReferenceOrganisationCmd referenceOrganisationCmd = new ReferenceOrganisationCmd(ORGANIZATION_ONE, ORGANIZATION_IDREFOG_ONE, lst);
        Mockito
            .when(referenceOrganizationUseCase.referenceOrganization(any(ReferenceOrganisationCmd.class)))
            .thenReturn(organization);

        String query = objectMapper.writeValueAsString(referenceOrganisationCmd);
        mockMvc
            // When
            .perform(MockMvcRequestBuilders.post("/api/V1/organizations")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(referenceOrganisationCmd)))
            // Then
            .andExpect(status().is((HttpStatus.CREATED.value())));
    }

    @Test
    @DisplayName("Retrieve a organization")
    void when_i_retrieve_the_detail_of_a_organization_http_status_is_ok() throws Exception {
        // Given
        Mockito
            .when(organizationQuery.getOrganizationTree(any()))
            .thenReturn(organization);

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.get("/api/V1/organizations/" + ORGANIZATION_IDREFOG_ONE)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isOk());
    }
}