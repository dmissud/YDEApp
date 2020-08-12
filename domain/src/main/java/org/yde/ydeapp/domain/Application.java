package org.yde.ydeapp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final String codeApplication;
    private String shortDescription;
    private String longDescription;
    private Personne responsable;
    private final Map<String, Note> notes;


    private Application(String codeApplication) {
        this.notes = new HashMap<>();
        this.codeApplication = codeApplication;
    }

    public String getCodeApplication() { return this.codeApplication; }

    public String getShortDescription() { return shortDescription; }

    public String getLongDescription() { return longDescription; }

    public Personne getResponsable() { return responsable; }


    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setResponsable(Personne responsable) {
        this.responsable = responsable;
    }


    public ApplicationIdent giveApplicationIdent() {
        return new ApplicationIdent(this.codeApplication, this.shortDescription);
    }

    public Map<String, Note> retrieveNotes() {
        return Collections.unmodifiableMap(notes);
    }

    public Note retrieveNoteByTitle(String noteTitle) {
        return notes.get(noteTitle);
    }


    public void addNote(Note newNote) {
        if (notes.get(newNote.getNoteTitle()) == null) {
            notes.put(newNote.getNoteTitle(), newNote);
        } else {
            notes.replace(newNote.getNoteTitle(), newNote);
        }
    }

    public static class Builder {
        private final String codeApplication;
        private String shortDescription = "to be completed";
        private String longDescription = "to be completed";
        private Personne responsable = null;

        public Builder(@NotNull String codeApplication) {
            this.codeApplication = codeApplication;
            log.trace("New builder Application");
        }

        public Builder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder withLongDescription(String longDescription) {
            this.longDescription = longDescription;
            return this;
        }

        public Builder withResponsable(Personne responsable) {
            this.responsable = responsable;
            return this;
        }


        public Application build() {
            Application application = new Application(this.codeApplication);
            application.shortDescription = this.shortDescription;
            application.longDescription = this.longDescription;
            application.responsable = this.responsable;
            log.trace("New Application Create");
            return application;
        }
    }
}
