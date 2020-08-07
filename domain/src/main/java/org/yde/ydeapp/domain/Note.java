package org.yde.ydeapp.domain;

public class Note {

    private final String noteContent;
    private final String noteVisibility;

    public Note(String noteContent, String noteVisibility) {
        this.noteContent = noteContent;
        this.noteVisibility = noteVisibility;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNoteVisibility() {
        return noteVisibility;
    }
}
