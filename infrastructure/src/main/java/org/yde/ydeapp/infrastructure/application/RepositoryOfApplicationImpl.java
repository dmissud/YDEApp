package org.yde.ydeapp.infrastructure.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.application.*;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;
import org.yde.ydeapp.infrastructure.organization.OrganizationEntity;
import org.yde.ydeapp.infrastructure.organization.RepositoryOfOrganizationJpa;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryOfApplicationImpl implements RepositoryOfApplication {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfApplicationImpl.class);

    @Autowired
    RepositoryOfApplicationJpa repositoryOfApplicationJpa;

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
        Personne personne = new Personne(applicationEntity.getResponsable().getUid(),
            applicationEntity.getResponsable().getFirstName(),
            applicationEntity.getResponsable().getLastName());
        OrganizationIdent organizationIdent = new OrganizationIdent(applicationEntity.getOrganisation().getIdRefog(), applicationEntity.getOrganisation().getName());
        CycleLife cycleLife = new CycleLife(applicationEntity.getCycleLife().getState(),
            applicationEntity.getCycleLife().getDateOfCreation(),
            applicationEntity.getCycleLife().getDateOfLastUpdate(),
            applicationEntity.getCycleLife().getDateEndInReality());
        ItSolution itSolution = new ItSolution(applicationEntity.getItSolution().getTypeOfSolution(),
                applicationEntity.getItSolution().getNameOfFirmware(),
                applicationEntity.getItSolution().getLabelOfSourcingMode());
        Criticity criticity = new Criticity(applicationEntity.getCriticity().getPrivilegeInformation(),
                applicationEntity.getCriticity().getPersonalData(),
                applicationEntity.getCriticity().getServiceClass(),
                applicationEntity.getCriticity().getAviability(),
                applicationEntity.getCriticity().getRpo(),
                applicationEntity.getCriticity().getRto());
        Application application = new Application.Builder(applicationEntity.getCodeApp())
            .withShortDescription(applicationEntity.getShortDescription())
            .withLongDescription(applicationEntity.getLongDescription())
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
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
        PersonneEntity responsableEntity = new PersonneEntity();
        responsableEntity.setUid(application.getResponsable().getUid());
        responsableEntity.setFirstName(application.getResponsable().getFirstName());
        responsableEntity.setLastName(application.getResponsable().getLastName());
        log.debug("Personne {} create", responsableEntity.getUid());
        CycleLifeEntity cycleLifeEntity = new CycleLifeEntity();
        cycleLifeEntity.setState(application.getCycleLife().getState());
        cycleLifeEntity.setDateOfCreation(application.getCycleLife().getDateOfCreation());
        cycleLifeEntity.setDateOfLastUpdate(application.getCycleLife().getDateOfLastUpdate());
        cycleLifeEntity.setDateEndInReality(application.getCycleLife().getDateEndInReality());

        ItSolutionEntity itSolutionEntity=new ItSolutionEntity();
        itSolutionEntity.setTypeOfSolution(application.getItSolution().getTypeOfSolution());
        itSolutionEntity.setLabelOfSourcingMode(application.getItSolution().getLabelOfSourcingMode());
        itSolutionEntity.setNameOfFirmware(application.getItSolution().getNameOfFirmware());

        CriticityEntity criticityEntity= new CriticityEntity();
        criticityEntity.setPrivilegeInformation(application.getCriticity().getPrivilegeInformation());
        criticityEntity.setPersonalData(application.getCriticity().getPersonalData());
        criticityEntity.setServiceClass(application.getCriticity().getServiceClass());
        criticityEntity.setAviability(application.getCriticity().getAviability());
        criticityEntity.setRpo(application.getCriticity().getRpo());
        criticityEntity.setRto(application.getCriticity().getRto());

        OrganizationEntity organizationEntity = repositoryOfOrganizationJpa.findByIdRefog(application.getOrganizationIdent().getIdRefog());
        applicationEntity = new ApplicationEntity();
        applicationEntity.setCodeApp(application.getCodeApplication());
        applicationEntity.setShortDescription(application.getShortDescription());
        applicationEntity.setLongDescription(application.getLongDescription());
        applicationEntity.setOrganisation(organizationEntity);
        applicationEntity.setResponsable(responsableEntity);
        applicationEntity.setCycleLife(cycleLifeEntity);
        applicationEntity.setItSolution(itSolutionEntity);
        applicationEntity.setCriticity(criticityEntity);
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

        updateCycleLife(application, applicationEntity);

        updateCriticity(application,applicationEntity);


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
                    application.getOrganizationIdent().getIdRefog(), applicationEntity.getCodeApp()));
            }
        }
    }

    private void updateResponsableRelationShip(Application application, ApplicationEntity applicationEntity) {
        if (!applicationEntity.getResponsable().getUid().equals(application.getResponsable().getUid())) {
            PersonneEntity responsableEntity = new PersonneEntity();
            responsableEntity.setUid(application.getResponsable().getUid());
            responsableEntity.setFirstName(application.getResponsable().getFirstName());
            responsableEntity.setLastName(application.getResponsable().getLastName());
            log.debug("Personne {} create", responsableEntity.getUid());
            applicationEntity.setResponsable(responsableEntity);
        }

        // Mapping des Notes

        List<NoteEntity> notesListEntity = new ArrayList<>();
        for (Note note : application.retrieveNotes().values()) {
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

    private void updateCycleLife(Application application, ApplicationEntity applicationEntity) {
        CycleLifeEntity cycleLifeEntity = new CycleLifeEntity();
        cycleLifeEntity.setState(application.getCycleLife().getState());
        cycleLifeEntity.setDateOfCreation(application.getCycleLife().getDateOfCreation());
        cycleLifeEntity.setDateOfLastUpdate(application.getCycleLife().getDateOfLastUpdate());
        cycleLifeEntity.setDateEndInReality(application.getCycleLife().getDateEndInReality());

        applicationEntity.setCycleLife(cycleLifeEntity);
    }

    private void updateCriticity(Application application, ApplicationEntity applicationEntity) {
        CriticityEntity criticityEntity = new CriticityEntity();
        criticityEntity.setPrivilegeInformation(application.getCriticity().getPrivilegeInformation());
        criticityEntity.setPersonalData(application.getCriticity().getPersonalData());
        criticityEntity.setServiceClass(application.getCriticity().getServiceClass());
        criticityEntity.setAviability(application.getCriticity().getAviability());
        criticityEntity.setRpo(application.getCriticity().getRpo());
        criticityEntity.setRto(application.getCriticity().getRto());

        applicationEntity.setCriticity(criticityEntity);
    }


}
