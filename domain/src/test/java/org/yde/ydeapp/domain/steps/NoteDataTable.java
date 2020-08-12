package org.yde.ydeapp.domain.steps;

public class NoteDataTable {

    private final String noteTitle;
    private final String noteContent;
    private final String noteCreationDate;

    public NoteDataTable(String noteTitle, String noteContent, String noteCreationDate) {
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
