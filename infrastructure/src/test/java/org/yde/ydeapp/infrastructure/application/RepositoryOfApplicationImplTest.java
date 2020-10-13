package org.yde.ydeapp.infrastructure.application;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yde.ydeapp.domain.application.*;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.infrastructure.organization.OrganizationEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Validation du Repository des Applications")
class RepositoryOfApplicationImplTest {

    //SimpleDateFormat formatter = new SimpleDateFormat(("dd/MM/yyyy"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    private static final String CODE_APP = "AP00001";
    private static final String SHORT_DESCRIPTION = "A short description";
    private static final String LONG_DESCRIPTION = "A long long description";
    private static final String UID_OF_RESPONSABLE = "123456";
    private static final String FIRSTNAME_OF_RESPONSABLE = "Jhon";
    private static final String LASTNAME_OF_RESPONSABLE = "Doe";
    private static final String STATE = "Active";
    private static final LocalDate DATE_OF_CREATION = LocalDate.of(2020, 1, 1);
    private static final LocalDate DATE_OF_LAST_UPDATE = LocalDate.of(2020, 1, 1);
    private static final LocalDate DATE_END_IN_REALITY = LocalDate.of(2020, 1, 1);
    private static final String CODE_APP_NOT_EXIST = "AP99999";
    private static final String NOTE_TITLE = "First Note";
    private static final String NOTE_CONTENT = "Once upon a time...";
    private static final LocalDate NOTE_CREATION_DATE = LocalDate.of(2020, 1, 2);
    private static final String NOTE_CONTENT_UPDATE = "The story continue...";
    private static final LocalDate NOTE_CREATION_DATE_UPDATE = LocalDate.of(2020, 3, 26);
    private static final String NOTE_2ND_TITLE = "Second Note";
    private static final String NOTE_2ND_CONTENT = "Another story...";
    private static final LocalDate NOTE_2ND_CREATION_DATE = LocalDate.of(2000, 6, 15);

    /*
    * data for ItSolution
     */
    public static final String TYPE_OF_SOLUTION = "10000000";
    public static final String NAME_OF_FIRMWARE = "NameOfFirmware";
    private static final String LABEL_OF_SOURCING = "IBM";

    /*
    * data for Criticity
     */

    private static final String PRIVILEGE_INFORMATION ="NON";
    private static final String PERSONAL_DATA ="OUI";
    private static final String SERVICE_CLASS ="Service minimum";
    private static final String AVAILABILITY ="C12";
    private static final String RPO ="01 J 01 H 12 MIN";
    private static final String RTO ="02 J 03 H 22 MIN";

    /*
     * data for Personne
     */

    public static final String ID_REFOG_MOE = "10000000";
    public static final String NAME_OF_MOE = "NAME_OF_MOE";
    public static final String ID_REFOG_MOE_OTHER = "10000001";
    public static final String NAME_OF_MOE_OTHER = "NAME_OF_OTHER_MOE";




    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RepositoryOfApplicationImpl repositoryOfApplicationImpl;

    RepositoryOfApplicationImplTest()  {
    }

    @Test
    @DisplayName("Find an existing application")
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
        assertThat(application.getItSolution()).isNotNull();
        assertThat(application.retrieveNotes()).isNotNull();
        assertThat(application.retrieveNoteByTitle(NOTE_TITLE)).isNotNull();
        assertThat(application.retrieveNoteByTitle(NOTE_TITLE).getNoteTitle()).isEqualTo(NOTE_TITLE);
    }

    @Test
    @DisplayName("Have null  when trying to find an unknown application")
    void When_Application_is_unknown_i_get_an_EntityNotExist_Exception() {
        // Given

        // When
        Application application = repositoryOfApplicationImpl.retrieveByAppCode(CODE_APP_NOT_EXIST);

        // Then
        Assertions.assertThat(application).isNull();
    }

    @Test
    @DisplayName("Couldn't create the application when it exists and get Exception")
    void should_have_EntityAlreadyExist_when_application_in_base() {
        // Given
        givenAApplicationEntityExistInBase();

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);
        ItSolution itSolution=new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING);
        Criticity criticity=new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO);
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE, NAME_OF_MOE);
        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
            .build();

        // When
        Throwable thrown = catchThrowable(() -> repositoryOfApplicationImpl.referenceApplication(application));

        // Then
        Assertions.assertThat(thrown).as("Essai creation application déjà en base").hasMessage(String.format("Application with %s is in repository", CODE_APP));
    }

    @Test
    @DisplayName("Create the application when it does not exist")
    void should_application_save_when_application_not_in_base() {
        // Given
        givenOrganizationExitInBase(ID_REFOG_MOE, NAME_OF_MOE);

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE, NAME_OF_MOE);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);
        ItSolution itSolution=new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING);
        Criticity criticity=new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO);

        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
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
        assertThat(((ApplicationEntity) lstApp.get(0)).getItSolution()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getCriticity()).isNotNull();
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
        ItSolution itSolution=new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING);
        Criticity criticity=new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO);


        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
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
        assertThat(((ApplicationEntity) lstApp.get(0)).getItSolution()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getCriticity()).isNotNull();

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
        List<NoteEntity> notes = new ArrayList<>();
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setNoteTitle(NOTE_TITLE);
        noteEntity.setNoteContent(NOTE_CONTENT);
        noteEntity.setNoteCreationDate(NOTE_CREATION_DATE);
        notes.add(noteEntity);

        noteEntity = new NoteEntity();
        noteEntity.setNoteTitle(NOTE_2ND_TITLE);
        noteEntity.setNoteContent(NOTE_2ND_CONTENT);
        noteEntity.setNoteCreationDate(NOTE_2ND_CREATION_DATE);
        notes.add(noteEntity);
        applicationEntity.setNotes(notes);

        CycleLifeEntity cycleLifeEntity = new CycleLifeEntity();
        cycleLifeEntity.setState(STATE);
        cycleLifeEntity.setDateOfCreation(DATE_OF_CREATION);
        cycleLifeEntity.setDateOfLastUpdate(DATE_OF_LAST_UPDATE);
        cycleLifeEntity.setDateEndInReality(DATE_END_IN_REALITY);

        applicationEntity.setCycleLife(cycleLifeEntity);

        ItSolutionEntity itSolutionEntity= new ItSolutionEntity();
        itSolutionEntity.setTypeOfSolution(TYPE_OF_SOLUTION);
        itSolutionEntity.setNameOfFirmware(NAME_OF_FIRMWARE);
        itSolutionEntity.setLabelOfSourcingMode(LABEL_OF_SOURCING);

        applicationEntity.setItSolution(itSolutionEntity);

        CriticityEntity criticityEntity =new CriticityEntity();
        criticityEntity.setPrivilegeInformation(PRIVILEGE_INFORMATION);
        criticityEntity.setPersonalData(PERSONAL_DATA);
        criticityEntity.setServiceClass(SERVICE_CLASS);
        criticityEntity.setAviability(AVAILABILITY);
        criticityEntity.setRpo(RPO);
        criticityEntity.setRto(RTO);
        applicationEntity.setCriticity(criticityEntity);


        testEntityManager.persistAndFlush(applicationEntity);
    }

    @Test
    @DisplayName("Create a new note for an existing application")
    void should_save_note_when_application_exists() {
        // Given
        givenAApplicationEntityExistInBase();

        // When
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE, NAME_OF_MOE);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);
        ItSolution itSolution=new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING);
        Criticity criticity=new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO);
        Personne responsable = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);

        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(responsable)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
            .build();

        Note noteUpdate = new Note(NOTE_TITLE, NOTE_CONTENT, NOTE_CREATION_DATE);
        application.storeOfNote(noteUpdate);

        repositoryOfApplicationImpl.updateApplication(application);

        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from ApplicationEntity c where c.codeApp = :codeAppAttenduDansQuery")
            .setParameter("codeAppAttenduDansQuery", CODE_APP)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteTitle()).isEqualTo(NOTE_TITLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteContent()).isEqualTo(NOTE_CONTENT);

    }

    @Test
    @DisplayName("Create a new note in an existing Noteslist")
    void should_save_new_note_when_notes_list_exists() {
        // Given
        givenAApplicationEntityExistInBase();

        // When
        Personne responsable = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);
        ItSolution itSolution=new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING);
        Criticity criticity=new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO);
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE, NAME_OF_MOE);
        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(responsable)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
            .build();

        Note noteInit = new Note(NOTE_TITLE, NOTE_CONTENT, NOTE_CREATION_DATE);
        application.storeOfNote(noteInit);

        repositoryOfApplicationImpl.updateApplication(application);

        Note notePlus = new Note(NOTE_2ND_TITLE, NOTE_2ND_CONTENT, NOTE_2ND_CREATION_DATE);
        application.storeOfNote(notePlus);

        repositoryOfApplicationImpl.updateApplication(application);

        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from ApplicationEntity c where c.codeApp = :codeAppAttenduDansQuery")
            .setParameter("codeAppAttenduDansQuery", CODE_APP)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().size()).isEqualTo(2);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteTitle()).isEqualTo(NOTE_TITLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteContent()).isEqualTo(NOTE_CONTENT);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(1).getNoteTitle()).isEqualTo(NOTE_2ND_TITLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(1).getNoteContent()).isEqualTo(NOTE_2ND_CONTENT);

    }

    @Test
    @DisplayName("Update an existing note")
    void should_update_new_note_when_note_already_exists() {
        // Given
        givenAApplicationEntityExistInBase();

        // When
        Personne responsable = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        CycleLife cycleLife = new CycleLife(STATE,DATE_OF_CREATION,DATE_OF_LAST_UPDATE,DATE_END_IN_REALITY);
        ItSolution itSolution=new ItSolution(TYPE_OF_SOLUTION,NAME_OF_FIRMWARE,LABEL_OF_SOURCING);
        Criticity criticity=new Criticity(PRIVILEGE_INFORMATION,PERSONAL_DATA,SERVICE_CLASS,AVAILABILITY,RPO,RTO);
        OrganizationIdent organizationIdent = new OrganizationIdent(ID_REFOG_MOE, NAME_OF_MOE);
        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(responsable)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
            .build();

        Note noteInit = new Note(NOTE_TITLE, NOTE_CONTENT, NOTE_CREATION_DATE);
        application.storeOfNote(noteInit);

        repositoryOfApplicationImpl.updateApplication(application);

        Note notePlus = new Note(NOTE_2ND_TITLE, NOTE_2ND_CONTENT, NOTE_2ND_CREATION_DATE);
        application.storeOfNote(notePlus);

        repositoryOfApplicationImpl.updateApplication(application);

        noteInit = new Note(NOTE_TITLE, NOTE_CONTENT_UPDATE, NOTE_CREATION_DATE_UPDATE);
        application.storeOfNote(noteInit);

        repositoryOfApplicationImpl.updateApplication(application);

        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from ApplicationEntity c where c.codeApp = :codeAppAttenduDansQuery")
            .setParameter("codeAppAttenduDansQuery", CODE_APP)
            .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().size()).isEqualTo(2);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteTitle()).isEqualTo(NOTE_TITLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteContent()).isEqualTo(NOTE_CONTENT_UPDATE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(1).getNoteTitle()).isEqualTo(NOTE_2ND_TITLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(1).getNoteContent()).isEqualTo(NOTE_2ND_CONTENT);

    }

}