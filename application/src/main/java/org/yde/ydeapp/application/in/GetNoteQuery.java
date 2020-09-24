package org.yde.ydeapp.application.in;

import org.yde.ydeapp.domain.application.Note;

import java.util.List;

public interface GetNoteQuery {


    Note getApplicationNoteByTitle(String codeApplication, String noteTitle);

    List<Note> getApplicationAllNotes(String codeApplication);

}
