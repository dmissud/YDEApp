package org.yde.ydeapp.domain;

import java.time.LocalDate;

public class Note {

    private final String noteTitle;
    private final String noteContent;
    private final LocalDate noteCreationDate;

    public Note(String noteTitle, String noteContent, LocalDate noteCreationDate) {
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
