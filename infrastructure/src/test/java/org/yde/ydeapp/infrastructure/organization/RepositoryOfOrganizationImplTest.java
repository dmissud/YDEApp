package org.yde.ydeapp.infrastructure.organization;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yde.ydeapp.domain.Organization;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Validation du Repository des Organizations")
class RepositoryOfOrganizationImplTest {

    private static final String NAME_ORGANIZATION_NOT_EXIST = "-Organization not exist-";
    public static final String ORGANIZATION_ALONE = "Organization Alone";
    public static final String ORGANIZATION_IDREFOG_ALONE = "100000";
    public static final String ORGANIZATION_ROOT = "Root Organization";
    public static final String ORGANIZATION_IDREFOG_ROOT = "200000";
    public static final String ORGANIZATION_CHILD_ONE = "Children the FIRST";
    public static final String ORGANIZATION_IDREFOG_CHILD_ONE = "200010";
    public static final String ORGANIZATION_CHILD_TWO = "Children the SECOND";
    public static final String ORGANIZATION_IDREFOG_CHILD_TWO = "200020";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RepositoryOfOrganizationImpl repositoryOfOrganizationImpl;

    @Test
    @DisplayName("Have a exception when try to find a non exist organization")
    void when_organization_not_exist_i_have_a_EntityNotExist_Exception() {
        // Given
        // No organization in th erepository
        // When
        Throwable thrown = catchThrowable(() -> repositoryOfOrganizationImpl.retrieveByIdRefog(NAME_ORGANIZATION_NOT_EXIST));

        // Then
        Assertions.assertThat(thrown).as("Essai recherche organization pas présente en base").hasMessage(String.format("Organization %s is not present in the Repository", NAME_ORGANIZATION_NOT_EXIST));
    }

    @Test
    void when_i_create_a_single_organization_i_have_a_new_entity() {
        // Given
        Organization organization = new Organization(ORGANIZATION_IDREFOG_ALONE, ORGANIZATION_ALONE);

        // When
        repositoryOfOrganizationImpl.referenceOrganization(organization);

        // Then
        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from OrganizationEntity c where c.name = :nameAttenduDansQuery")
            .setParameter("nameAttenduDansQuery", ORGANIZATION_ALONE)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((OrganizationEntity) lstApp.get(0)).getChildren().size()).isZero();
    }

    @Test
    void when_i_create_a_tree_organization_i_have_a_new_set_of_entity() {
        // Given
        Organization organization = new Organization(ORGANIZATION_IDREFOG_ALONE, ORGANIZATION_ROOT);
        organization.addChild(new Organization(ORGANIZATION_IDREFOG_CHILD_ONE, ORGANIZATION_CHILD_ONE));
        organization.addChild(new Organization(ORGANIZATION_IDREFOG_CHILD_TWO, ORGANIZATION_CHILD_TWO));

        // When
        repositoryOfOrganizationImpl.referenceOrganization(organization);

        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from OrganizationEntity c where c.name = :nameAttenduDansQuery")
            .setParameter("nameAttenduDansQuery", ORGANIZATION_ROOT)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((OrganizationEntity) lstApp.get(0)).getChildren().size()).isEqualTo(2);
        lstApp = testEntityManager.getEntityManager().createQuery("select c from OrganizationEntity c where c.name = :nameAttenduDansQuery")
            .setParameter("nameAttenduDansQuery", ORGANIZATION_CHILD_ONE)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((OrganizationEntity) lstApp.get(0)).getChildren().size()).isZero();
        lstApp = testEntityManager.getEntityManager().createQuery("select c from OrganizationEntity c where c.name = :nameAttenduDansQuery")
            .setParameter("nameAttenduDansQuery", ORGANIZATION_CHILD_TWO)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((OrganizationEntity) lstApp.get(0)).getChildren().size()).isZero();
    }
}