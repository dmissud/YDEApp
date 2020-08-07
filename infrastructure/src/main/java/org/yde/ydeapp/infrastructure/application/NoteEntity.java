package org.yde.ydeapp.infrastructure.application;

import javax.persistence.*;

@Entity
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String noteContent;
    private String noteVisibility;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteVisibility() {
        return noteVisibility;
    }

    public void setNoteVisibility(String noteVisibility) {
        this.noteVisibility = noteVisibility;
    }
}
