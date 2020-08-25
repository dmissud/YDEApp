package org.yde.ydeapp.infrastructure.application;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.CycleLife;
import org.yde.ydeapp.domain.OrganizationIdent;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.infrastructure.organization.OrganizationEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Validation du Repository des Applications")
class RepositoryOfApplicationImplTest {

    SimpleDateFormat formatter = new SimpleDateFormat(("dd/MM/yyyy"));

    private static final String CODE_APP = "AP00001";
    private static final String SHORT_DESCRIPTION = "A short description";
    private static final String LONG_DESCRIPTION = "A long long description";
    private static final String UID_OF_RESPONSABLE = "123456";
    private static final String FIRSTNAME_OF_RESPONSABLE = "Jhon";
    private static final String LASTNAME_OF_RESPONSABLE = "Doe";
    private static final String STATE = "Active";
    private final Date DATE_OF_CREATION = formatter.parse("01/01/2020");
    private final Date DATE_OF_LAST_UPDATE = formatter.parse("01/01/2020");
    private final Date DATE_END_IN_REALITY = formatter.parse("01/01/2020");
    private static final String CODE_APP_NOT_EXIST = "AP99999";
    public static final String ID_REFOG_MOE = "10000000";
    public static final String NAME_OF_MOE = "NAME_OF_MOE";
    public static final String ID_REFOG_MOE_OTHER = "10000001";
    public static final String NAME_OF_MOE_OTHER = "NAME_OF_OTHER_MOE";
    private final Date DATE_OF_CREATION_OTHER = formatter.parse("01/01/2020");
    private final Date DATE_OF_LAST_UPDATE_OTHER = formatter.parse("01/01/2020");
    private final Date DATE_END_IN_REALITY_OTHER = formatter.parse("01/01/2020");


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RepositoryOfApplicationImpl repositoryOfApplicationImpl;

    RepositoryOfApplicationImplTest() throws ParseException {
    }

    @Test
    @DisplayName("Find a exiting application")
    void When_Application_exist_i_should_retrieve_it() {
        // Given
        givenAApplicationEntityExistInBase();

        // When
        Application application = repositoryOfApplicationImpl.retrieveByAppCode(CODE_APP);

        // Then
        assertThat(application).isNotNull();
        assertThat(application.getCodeApplication()).isEqualTo(CODE_APP);
        assertThat(application.getResponsable()).isNotNull();
        assertThat(application.getResponsable().getUid()).isEqualTo(UID_OF_RESPONSABLE);
        assertThat(application.getOrganizationIdent()).isNotNull();
        assertThat(application.getOrganizationIdent().getIdRefog()).isEqualTo(ID_REFOG_MOE);
        assertThat(application.getCycleLife()).isNotNull();
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
        givenAApplicationEntityExistInBase();

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);
        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .withCycleLife(cycleLife)
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
        givenOrganizationExitInBase(ID_REFOG_MOE, NAME_OF_MOE);

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE, NAME_OF_MOE);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);

        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
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
        assertThat(((ApplicationEntity) lstApp.get(0)).getCycleLife()).isNotNull();
    }

    @Test
    @DisplayName("Update the application when it's exist")
    void should_application_update_when_application_is_in_base() {
        // Given
        givenAApplicationEntityExistInBase();

        givenOrganizationExitInBase(ID_REFOG_MOE_OTHER, NAME_OF_MOE_OTHER);

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE_OTHER, NAME_OF_MOE_OTHER);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);


        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .build();

        // When
        repositoryOfApplicationImpl.updateApplication(application);

        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from ApplicationEntity c where c.codeApp = :codeAppAttenduDansQuery")
            .setParameter("codeAppAttenduDansQuery", CODE_APP)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getResponsable()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getResponsable().getUid()).isEqualTo(UID_OF_RESPONSABLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation().getIdRefog()).isEqualTo(ID_REFOG_MOE_OTHER);
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation().getApplications().size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation().getApplications().get(0)).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getOrganisation().getApplications().get(0).getCodeApp()).isEqualTo(CODE_APP);
        assertThat(((ApplicationEntity) lstApp.get(0)).getCycleLife()).isNotNull();

        List lstOrga = testEntityManager.getEntityManager().createQuery("select c from OrganizationEntity c where c.idRefog = :idRefogAttenduDansQuery")
            .setParameter("idRefogAttenduDansQuery", ID_REFOG_MOE)
            .getResultList();
        assertThat(lstOrga.size()).isEqualTo(1);
        assertThat(((OrganizationEntity)lstOrga.get(0)).getApplications().size()).isZero();
    }

    private void givenOrganizationExitInBase(String idRefogMoeOther, String nameOfMoeOther) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setIdRefog(idRefogMoeOther);
        organizationEntity.setName(nameOfMoeOther);
        List<ApplicationEntity> applicationEntitys = new ArrayList<>();
        organizationEntity.setApplications(applicationEntitys);
        testEntityManager.persistAndFlush(organizationEntity);
    }

    private void givenAApplicationEntityExistInBase() {
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

        CycleLifeEntity cycleLifeEntity = new CycleLifeEntity();
        cycleLifeEntity.setState(STATE);
        cycleLifeEntity.setDateOfCreation(DATE_OF_CREATION);
        cycleLifeEntity.setDateOfLastUpdate(DATE_OF_LAST_UPDATE);
        cycleLifeEntity.setDateEndInReality(DATE_END_IN_REALITY);

        applicationEntity.setCycleLife(cycleLifeEntity);


        testEntityManager.persistAndFlush(applicationEntity);
    }

}