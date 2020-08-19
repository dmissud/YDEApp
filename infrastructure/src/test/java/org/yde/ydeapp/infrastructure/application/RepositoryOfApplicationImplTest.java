package org.yde.ydeapp.infrastructure.application;


import com.sun.xml.bind.v2.model.core.ID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.Organization;
import org.yde.ydeapp.domain.OrganizationIdent;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.infrastructure.organization.OrganizationEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Validation du Repository des Applications")
class RepositoryOfApplicationImplTest {


    private static final String CODE_APP = "AP00001";
    private static final String SHORT_DESCRIPTION = "A short description";
    private static final String LONG_DESCRIPTION = "A long long description";
    private static final String UID_OF_RESPONSABLE = "123456";
    private static final String FIRSTNAME_OF_RESPONSABLE = "Jhon";
    private static final String LASTNAME_OF_RESPONSABLE = "Doe";
    private static final String CODE_APP_NOT_EXIST = "AP99999";
    public static final String ID_REFOG_MOE = "10000000";
    public static final String NAME_OF_MOE = "NAME_OF_MOE";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RepositoryOfApplicationImpl repositoryOfApplicationImpl;

    @Test
    @DisplayName("Find a exiting application")
    void When_Application_exist_i_should_retrieve_it() {
        // Given
        GivenAApplicationEntityExistInBase();

        // When
        Application application = repositoryOfApplicationImpl.retrieveByAppCode(CODE_APP);

        // Then
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(CODE_APP);
        assertThat(application.getResponsable()).isNotNull();
        assertThat(application.getResponsable().getUid()).isEqualTo(UID_OF_RESPONSABLE);
        assertThat(application.getOrganizationIdent()).isNotNull();
        assertThat(application.getOrganizationIdent().getIdRefog()).isEqualTo(ID_REFOG_MOE);
    }

    @Test
    @DisplayName("Have a null return when try to find a non exist application")
    void When_Application_not_exist_i_have_a_null_value() {
        // Given

        // When
        Application application = repositoryOfApplicationImpl.retrieveByAppCode(CODE_APP_NOT_EXIST);

        // Then
        Assertions.assertThat(application).isNull();
    }

    @Test
    @DisplayName("Couldn't create the application when it's exist and got Exception")
    void should_have_EntityAlreadyExist_when_application_in_base() {
        // Given
        GivenAApplicationEntityExistInBase();

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .build();

        // When
        Throwable thrown = catchThrowable(() -> repositoryOfApplicationImpl.referenceApplication(application));

        // Then
        Assertions.assertThat(thrown).as("Essai creation application déjà en base").hasMessage(String.format("Application with %s is in repository", CODE_APP));
    }

    @Test
    @DisplayName("Create the application when it's not exist")
    void should_application_save_when_application_not_in_base() {
        // Given
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setIdRefog(ID_REFOG_MOE);
        organizationEntity.setName(NAME_OF_MOE);
        List<ApplicationEntity> applicationEntitys = new ArrayList<>();
        organizationEntity.setApplications(applicationEntitys);
        testEntityManager.persistAndFlush(organizationEntity);

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE, NAME_OF_MOE);

        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .build();

        // When
        repositoryOfApplicationImpl.referenceApplication(application);

        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from ApplicationEntity c where c.codeApp = :codeAppAttenduDansQuery")
            .setParameter("codeAppAttenduDansQuery", CODE_APP)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getResponsable()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getResponsable().getUid()).isEqualTo(UID_OF_RESPONSABLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation().getApplications().size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation().getApplications().get(0)).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation().getApplications().get(0).getCodeApp()).isEqualTo(CODE_APP);
    }

    private void GivenAApplicationEntityExistInBase() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setCodeApp(CODE_APP);
        applicationEntity.setShortDescription(SHORT_DESCRIPTION);
        applicationEntity.setLongDescription(LONG_DESCRIPTION);

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setIdRefog(ID_REFOG_MOE);
        organizationEntity.setName(NAME_OF_MOE);
        List<ApplicationEntity> applicationEntitys = new ArrayList<>();
        applicationEntitys.add(applicationEntity);
        organizationEntity.setApplications(applicationEntitys);

        applicationEntity.setOrganisation(organizationEntity);

        PersonneEntity personneEntity = new PersonneEntity();
        personneEntity.setUid(UID_OF_RESPONSABLE);
        personneEntity.setFirstName(FIRSTNAME_OF_RESPONSABLE);
        personneEntity.setLastName(LASTNAME_OF_RESPONSABLE);

        applicationEntity.setResponsable(personneEntity);

        testEntityManager.persistAndFlush(applicationEntity);
    }

}