package org.yde.ydeapp.infrastructure.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;
import org.yde.ydeapp.domain.Note;
import org.yde.ydeapp.domain.OrganizationIdent;
import org.yde.ydeapp.domain.Personne;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;
import org.yde.ydeapp.infrastructure.organization.OrganizationEntity;
import org.yde.ydeapp.infrastructure.organization.RepositoryOfOrganizationJpa;

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

    @Autowired
    RepositoryOfOrganizationJpa repositoryOfOrganizationJpa;

    @Override
    public Application retrieveByAppCode(String codeApp) {
        ApplicationEntity applicationEntity = repositoryOfApplicationJpa.findByCodeApp(codeApp);

        if (applicationEntity == null) {
            log.debug("Application {} not exist", codeApp);
            return null;
        }
        log.debug("Application {} load", codeApp);

        // Mapping du responsable
        Personne personne = new Personne(applicationEntity.getResponsable().getUid(), applicationEntity.getResponsable().getFirstName(), applicationEntity.getResponsable().getLastName());

        Application application = new Application.Builder(applicationEntity.getCodeApp())
        OrganizationIdent organizationIdent = new OrganizationIdent(applicationEntity.getOrganisation().getIdRefog(), applicationEntity.getOrganisation().getName());

        return new Application.Builder(applicationEntity.getCodeApp())
            .withShortDescription(applicationEntity.getShortDescription())
            .withLongDescription(applicationEntity.getLongDescription())
            .withResponsable(personne)
            .withOrganization(organizationIdent)
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

        OrganizationEntity organizationEntity = repositoryOfOrganizationJpa.findByIdRefog(application.getOrganizationIdent().getIdRefog());
        applicationEntity = new ApplicationEntity();
        applicationEntity.setCodeApp(application.getCodeApplication());
        applicationEntity.setShortDescription(application.getShortDescription());
        applicationEntity.setLongDescription(application.getLongDescription());
        applicationEntity.setOrganisation(organizationEntity);
        applicationEntity.setResponsable(responsableEntity);
        organizationEntity.getApplications().add(applicationEntity);
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

        updateResponsableRelationShip(application, applicationEntity);

        updateOrganizationRelationShip(application, applicationEntity);

        log.debug("Application {} update", application.getCodeApplication());

        repositoryOfApplicationJpa.save(applicationEntity);
    }

    private void updateOrganizationRelationShip(Application application, ApplicationEntity applicationEntity) {
        if (!applicationEntity.getOrganisation().getIdRefog().equals(application.getOrganizationIdent().getIdRefog())) {
            OrganizationEntity organizationEntity = repositoryOfOrganizationJpa.findByIdRefog(application.getOrganizationIdent().getIdRefog());
            if (organizationEntity != null) {
                organizationEntity.getApplications().add(applicationEntity);
                applicationEntity.getOrganisation().getApplications().remove(applicationEntity);
                applicationEntity.setOrganisation(organizationEntity);
                log.debug("Organization {} link   to application {}", organizationEntity.getName(), applicationEntity.getCodeApp());
            } else {
                throw new EntityNotFound(String.format("Organization %s in not in the repository, coundn't link %s to",
                    application.getOrganizationIdent().getIdRefog(), applicationEntity.getCodeApp() ));
            }
        }
    }

    private void updateResponsableRelationShip(Application application, ApplicationEntity applicationEntity) {
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
