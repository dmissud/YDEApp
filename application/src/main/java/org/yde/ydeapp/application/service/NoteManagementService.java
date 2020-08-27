package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.GetNoteQuery;
import org.yde.ydeapp.application.in.ReferenceNoteUseCase;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.Note;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
@Transactional
public class NoteManagementService implements ReferenceNoteUseCase,GetNoteQuery {

    private final Logger log = LoggerFactory.getLogger(NoteManagementService.class);

    @Autowired
    RepositoryOfApplication repositoryOfApplication;

    @Override
    public Note referenceNote(String codeApplication, ReferenceNoteCmd referenceNoteCmd) {
        Application application = repositoryOfApplication.retrieveByAppCode(codeApplication);

        Note note = new Note(referenceNoteCmd.getNoteTitle(), referenceNoteCmd.getNoteContent(), referenceNoteCmd.getNoteCreationDate());
        application.addNote(note);

        repositoryOfApplication.updateApplication(application);
        log.trace("For application {} user referenced a new note entitled {}", application.getCodeApplication(), note.getNoteTitle());

        return note;
    }

    @Override
    public Note updateNote(String codeApplication, String noteTitle, ReferenceNoteCmd referenceNoteCmd) {

        Application application  = repositoryOfApplication.retrieveByAppCode(codeApplication);
        Note note = new Note(noteTitle, referenceNoteCmd.getNoteContent(), referenceNoteCmd.getNoteCreationDate());
        application.addNote(note);

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

        List<Note> allNotes = new ArrayList<>();
        //Iterator iter = repositoryOfApplication.retrieveByAppCode(codeApplication).retrieveNotes().entrySet().iterator();
        allNotes.addAll(repositoryOfApplication.retrieveByAppCode(codeApplication).retrieveNotes().values());


        log.trace("Notes list for application {} has been sent.", codeApplication);

        return allNotes;
    }
}
