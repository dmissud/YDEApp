package org.yde.ydeapp.infrastructure.application;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class NoteEntity {
    private String noteTitle;
    private String noteContent;
    private LocalDate noteCreationDate;


    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public void setNoteCreationDate(LocalDate noteCreationDate) {
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
