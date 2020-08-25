package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.Note;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface ReferenceNoteUseCase {

    Note referenceNote(String codeApplication, ReferenceNoteCmd referenceNoteCmd);

    Note updateNote(String codeApplication, String noteTitle, ReferenceNoteCmd referenceNoteCmd);

    @Validated
    class ReferenceNoteCmd extends SelfValidating<ReferenceNoteCmd> {

        @Size(min = 1, max = 20)
        @Pattern(regexp = "^{20}$")
        private final String noteTitle;

        private final String noteContent;
        private final String noteCreationDate;

        public ReferenceNoteCmd(String noteTitle, String noteContent, String noteCreationDate) {
            this.noteTitle = noteTitle;
            this.noteContent = noteContent;
            this.noteCreationDate = noteCreationDate;
        }

        public String getNoteTitle() {
            return noteTitle;
        }

        public String getNoteContent() {
            return noteContent;
        }

        public String getNoteCreationDate() {
            return noteCreationDate;
        }
    }

}
