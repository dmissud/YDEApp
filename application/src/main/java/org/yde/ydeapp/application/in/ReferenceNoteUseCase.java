package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.application.Note;

import javax.validation.constraints.Size;
import java.time.LocalDate;

public interface ReferenceNoteUseCase {

    Note referenceNote(String codeApplication, ReferenceNoteCmd referenceNoteCmd);

    Note updateNote(String codeApplication, String noteTitle, ReferenceNoteCmd referenceNoteCmd);

    void deleteNoteByTitle(String codeApplication, String noteTitle);


    @Validated
    class ReferenceNoteCmd extends SelfValidating<ReferenceNoteCmd> {

        @Size(min = 1, max = 20)
        //@Pattern(regexp = "^{20}$")
        private final String noteTitle;

        private final String noteContent;
        private final LocalDate noteCreationDate;

        public ReferenceNoteCmd(String noteTitle, String noteContent, LocalDate noteCreationDate) {
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

        public LocalDate getNoteCreationDate() {
            return noteCreationDate;
        }
    }

}
