package org.yde.ydeapp.infrastructure.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;
import org.yde.ydeapp.domain.Note;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryOfApplicationImpl implements RepositoryOfApplication {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfApplicationImpl.class);

    @Autowired
    RepositoryOfApplicationJpa repositoryOfApplicationJpa;

    @Autowired
    RepositoryOfPersonneJpa repositoryOfPersonneJpa;

    @Override
    public Application retrieveByAppCode(String codeApp) {
        ApplicationEntity applicationEntity = repositoryOfApplicationJpa.findByCodeApp(codeApp);

        if (applicationEntity == null) {
            log.debug("Application {} not exist", codeApp);
            throw new EntityNotFound(String.format("Application with %s is not in repository", codeApp));
        }
        log.debug("Application {} load", codeApp);

        // Mapping du responsable
        Personne personne = new Personne(applicationEntity.getResponsable().getUid(), applicationEntity.getResponsable().getFirstName(), applicationEntity.getResponsable().getLastName());

        Application application = new Application.Builder(applicationEntity.getCodeApp())
            .withShortDescription(applicationEntity.getShortDescription())
            .withLongDescription(applicationEntity.getLongDescription())
            .withResponsable(personne)
            .build();

        // Mapping des Notes
        for (NoteEntity noteEntity : applicationEntity.getNotes()) {
            Note note = new Note(noteEntity.getNoteTitle(),
                    noteEntity.getNoteContent(),
                    noteEntity.getNoteCreationDate());
            application.addNote(note);
        }

        return application;
    }


    @Override
    public void referenceApplication(Application application) {
        ApplicationEntity applicationEntity = repositoryOfApplicationJpa.findByCodeApp(application.getCodeApplication());
        if (applicationEntity != null) {
            log.debug("Application {} with id {} already exist", applicationEntity.getCodeApp(), applicationEntity.getId());
            throw new EntityAlreadyExist(String.format("Application with %s is in repository", applicationEntity.getCodeApp()));
        }
        PersonneEntity responsableEntity = repositoryOfPersonneJpa.findByUid(application.getResponsable().getUid());
        if (responsableEntity == null) {
            responsableEntity = new PersonneEntity();
            responsableEntity.setUid(application.getResponsable().getUid());
            responsableEntity.setFirstName(application.getResponsable().getFirstName());
            responsableEntity.setLastName(application.getResponsable().getLastName());
            log.debug("Personne {} create", responsableEntity.getUid());
        }

        applicationEntity = new ApplicationEntity();
        applicationEntity.setCodeApp(application.getCodeApplication());
        applicationEntity.setShortDescription(application.getShortDescription());
        applicationEntity.setLongDescription(application.getLongDescription());

        applicationEntity.setResponsable(responsableEntity);
        log.debug("Application {} create", application.getCodeApplication());

        repositoryOfApplicationJpa.save(applicationEntity);
    }

    @Override
    public List<ApplicationIdent> retrieveIdentOfAllApplications() {
        return repositoryOfApplicationJpa.retrieveApplicationsIdent();
    }

    @Override
    public void updateApplication(Application application) {
        ApplicationEntity applicationEntity = repositoryOfApplicationJpa.findByCodeApp(application.getCodeApplication());
        if (applicationEntity == null) {
            log.error("Application {} does not exist", application.getCodeApplication());
            throw new EntityNotFound(String.format("Application with %s is not in repository", application.getCodeApplication()));
        }

        applicationEntity.setShortDescription(application.getShortDescription());
        applicationEntity.setLongDescription(application.getLongDescription());

        if (!applicationEntity.getResponsable().getUid().equals(application.getResponsable().getUid())) {
            PersonneEntity responsableEntity = repositoryOfPersonneJpa.findByUid(application.getResponsable().getUid());
            if (responsableEntity == null) {
                responsableEntity = new PersonneEntity();
                responsableEntity.setUid(application.getResponsable().getUid());
                responsableEntity.setFirstName(application.getResponsable().getFirstName());
                responsableEntity.setLastName(application.getResponsable().getLastName());
                log.debug("Personne {} create", responsableEntity.getUid());
            }
            applicationEntity.setResponsable(responsableEntity);
        }

        // Mapping des Notes

        List<NoteEntity> notesListEntity = new ArrayList<>();
        for(Note note : application.retrieveNotes().values()) {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setNoteTitle(note.getNoteTitle());
            noteEntity.setNoteContent(note.getNoteContent());
            noteEntity.setNoteCreationDate(note.getNoteCreationDate());
            notesListEntity.add(noteEntity);

        }
        applicationEntity.setNotes(notesListEntity);

        log.debug("Application {} update", application.getCodeApplication());

        repositoryOfApplicationJpa.save(applicationEntity);
    }
}
