package org.yde.ydeapp.infrastructure.application;

import javax.persistence.*;

@Embeddable
public class NoteEntity {


    @Column(unique = true)
    private String noteTitle;
    private String noteContent;
    private String noteCreationDate;


    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public void setNoteCreationDate(String noteCreationDate) {
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
