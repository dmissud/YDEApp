package org.yde.ydeapp.application.in.application;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.application.Note;

public interface UpdateNoteUseCase {


    Note updateExistingNote(String codeApplication, String noteTitle, UpdateNoteCmd updateNoteCmd);

    @Validated
    class UpdateNoteCmd extends SelfValidating<UpdateNoteCmd> {
        private final String noteContent;
        public UpdateNoteCmd( String noteContent) {
            this.noteContent = noteContent;
        }
        public String getNoteContent() {
            return noteContent;
        }
    }
}
