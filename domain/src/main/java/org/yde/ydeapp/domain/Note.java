package org.yde.ydeapp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Note {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private String noteTitle;
    private String noteContent;
    private String noteCreationDate;

    public Note(String noteTitle, String noteContent, String noteCreationDate) {
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
