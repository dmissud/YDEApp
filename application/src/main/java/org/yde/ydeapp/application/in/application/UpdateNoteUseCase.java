package org.yde.ydeapp.application.in.application;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.application.Note;

public interface UpdateNoteUseCase {
    Note updateExistingNote(String codeApplication, UpdateNoteCmd updateNoteCmd);

    @Validated
    class UpdateNoteCmd extends SelfValidating<UpdateNoteCmd> {
        private final String noteTitle;
        private final String noteContent;
        public UpdateNoteCmd( String noteTitle, String noteContent) {
            this.noteTitle = noteTitle;
            this.noteContent = noteContent;
        }
        public String getNoteTitle() {
            return noteTitle;
        }
        public String getNoteContent() {
            return noteContent;
        }
    }
}
