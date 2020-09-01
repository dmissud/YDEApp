package org.yde.ydeapp.domain.steps;

import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.CycleLife;
import org.yde.ydeapp.domain.OrganizationIdent;
import org.yde.ydeapp.domain.Personne;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
