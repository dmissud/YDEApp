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
import org.yde.ydeapp.domain.Note;
import org.yde.ydeapp.domain.Personne;

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
    private static final String NOTE_TITLE = "First Note";
    private static final String NOTE_CONTENT = "Once upon a time...";
    private static final String NOTE_CREATION_DATE = "01/02/2020";
    private static final String NOTE_CONTENT_UPDATE = "The story continue...";
    private static final String NOTE_CREATION_DATE_UPDATE = "26/03/2020";



    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RepositoryOfApplicationImpl repositoryOfApplicationImpl;

    @Test
    @DisplayName("Find an existing application")
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
        assertThat(application.retrieveNotes()).isNotNull();
        assertThat(application.retrieveNoteByTitle(NOTE_TITLE)).isNotNull();
        assertThat(application.retrieveNoteByTitle(NOTE_TITLE).getNoteTitle()).isEqualTo(NOTE_TITLE);
    }

    @Test
    @DisplayName("Throw a new exception when trying to find an unknown application")
    void When_Application_is_unknown_i_get_an_EntityNotExist_Exception() {
        // Given

        // When
        Throwable thrown = catchThrowable(() -> repositoryOfApplicationImpl.retrieveByAppCode(CODE_APP_NOT_EXIST));

        // Then
        Assertions.assertThat(thrown).as("Essai recherche application pas présente en base").hasMessage(String.format("Application with %s is not in repository", CODE_APP_NOT_EXIST));
    }

    private void GivenAApplicationEntityExistInBase() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setCodeApp(CODE_APP);
        applicationEntity.setShortDescription(SHORT_DESCRIPTION);
        applicationEntity.setLongDescription(LONG_DESCRIPTION);

        PersonneEntity personneEntity = new PersonneEntity();
        personneEntity.setUid(UID_OF_RESPONSABLE);
        personneEntity.setFirstName(FIRSTNAME_OF_RESPONSABLE);
        personneEntity.setLastName(LASTNAME_OF_RESPONSABLE);

        List<NoteEntity> notes = new ArrayList<>();
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setNoteTitle(NOTE_TITLE);
        noteEntity.setNoteContent(NOTE_CONTENT);
        noteEntity.setNoteCreationDate(NOTE_CREATION_DATE);
        notes.add(noteEntity);

        //testEntityManager.persistAndFlush(personneEntity);
        applicationEntity.setResponsable(personneEntity);
        applicationEntity.setNotes(notes);

        testEntityManager.persistAndFlush(applicationEntity);
    }

    @Test
    @DisplayName("Couldn't create the application when it exists and get Exception")
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
    @DisplayName("Create the application when it does not exist")
    void should_application_save_when_application_not_in_base() {
        // Given

        // Application not in base
        Personne personne = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        Application application = new Application.Builder(CODE_APP)
            .withShortDescription(SHORT_DESCRIPTION)
            .withLongDescription(LONG_DESCRIPTION)
            .withResponsable(personne)
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
    }

    @Test
    @DisplayName("Update a new note for an existing application")
    void should_save_note_when_application_exists() {
        // Given
        GivenAApplicationEntityExistInBase();

        // When
        Personne responsable = new Personne(UID_OF_RESPONSABLE, FIRSTNAME_OF_RESPONSABLE, LASTNAME_OF_RESPONSABLE);
        Application application = new Application.Builder(CODE_APP)
                .withShortDescription(SHORT_DESCRIPTION)
                .withLongDescription(LONG_DESCRIPTION)
                .withResponsable(responsable)
                .build();

        Note noteUpdate = new Note(NOTE_TITLE, NOTE_CONTENT_UPDATE, NOTE_CREATION_DATE_UPDATE);
        application.addNote(noteUpdate);

        repositoryOfApplicationImpl.updateApplication(application);

        // Then
        List lstApp = testEntityManager.getEntityManager().createQuery("select c from ApplicationEntity c where c.codeApp = :codeAppAttenduDansQuery")
                .setParameter("codeAppAttenduDansQuery", CODE_APP)
                .getResultList();
        assertThat(lstApp.size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes()).isNotNull();
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().size()).isEqualTo(1);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteTitle()).isEqualTo(NOTE_TITLE);
        assertThat(((ApplicationEntity) lstApp.get(0)).getNotes().get(0).getNoteContent()).isEqualTo(NOTE_CONTENT_UPDATE);

    }

}