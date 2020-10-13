package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.application.GetNoteQuery;
import org.yde.ydeapp.application.in.application.ReferenceNoteUseCase;
import org.yde.ydeapp.application.in.application.UpdateNoteUseCase;
import org.yde.ydeapp.domain.application.Application;
import org.yde.ydeapp.domain.application.Note;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class NoteManagementService implements ReferenceNoteUseCase, GetNoteQuery, UpdateNoteUseCase {

    private final Logger log = LoggerFactory.getLogger(NoteManagementService.class);

    @Autowired
    RepositoryOfApplication repositoryOfApplication;

    @Override
    public Note referenceNote(String codeApplication, ReferenceNoteCmd referenceNoteCmd) {
        Application application = repositoryOfApplication.retrieveByAppCode(codeApplication);

        Note note = new Note(referenceNoteCmd.getNoteTitle(), referenceNoteCmd.getNoteContent(), referenceNoteCmd.getNoteCreationDate());
        application.storeOfNote(note);

        repositoryOfApplication.updateApplication(application);
        log.trace("For application {} user referenced a new note entitled {}", application.getCodeApplication(), note.getNoteTitle());

        return note;
    }

    @Override
    public Note updateExistingNote(String codeApplication, String noteTitle, UpdateNoteCmd updateNoteCmd) {

        Application application = repositoryOfApplication.retrieveByAppCode(codeApplication);

        Note note = application.storeOfNote(noteTitle, updateNoteCmd.getNoteContent());

        repositoryOfApplication.updateApplication(application);
        log.trace("For application {} user updated an existing note entitled {}", application.getCodeApplication(), note.getNoteTitle());

        return note;
    }

    @Override
    public Note getApplicationNoteByTitle(String codeApplication, String noteTitle) {

        Application application = repositoryOfApplication.retrieveByAppCode(codeApplication);
        Note note = application.retrieveNoteByTitle(noteTitle);

        log.trace("Note entitled {} has been sent.", note.getNoteTitle());

        return note;
    }

    @Override
    public List<Note> getApplicationAllNotes(String codeApplication) {

        List<Note> allNotes = new ArrayList<>(repositoryOfApplication.retrieveByAppCode(codeApplication).retrieveNotes().values());

        log.trace("Notes list for application {} has been sent.", codeApplication);

        return allNotes;
    }

    @Override
    public void deleteNoteByTitle(String codeApplication, String noteTitle) {

        Application application = repositoryOfApplication.retrieveByAppCode(codeApplication);
        if (application.retrieveNoteByTitle(noteTitle) != null) {
            application.deleteNote(noteTitle);
            repositoryOfApplication.updateApplication(application);
        } else {
            log.error("Note {} not exist on {}", noteTitle, codeApplication);
            throw new EntityNotFound(String.format("Note %s does not exist", noteTitle));
        }
        log.trace("Note {} of {} has been deleted.", noteTitle, codeApplication);

    }
}
