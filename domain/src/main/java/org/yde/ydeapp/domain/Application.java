package org.yde.ydeapp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yde.ydeapp.domain.out.EntityIncorrect;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final String codeApplication;
    private String shortDescription;
    private String longDescription;
    private Personne responsable;
    private OrganizationIdent organizationIdent;
    private CycleLife cycleLife;
    private final Map<String, Note> notes;


    private Application(String codeApplication) {
        this.notes = new HashMap<>();
        this.codeApplication = codeApplication;
    }

    public String getCodeApplication() { return this.codeApplication; }

    public String getShortDescription() { return shortDescription; }

    public String getLongDescription() { return longDescription; }

    public Personne getResponsable() { return responsable; }

    public CycleLife getCycleLife() {
        return cycleLife;
    }

    public void updateShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void updateLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void updateResponsable(Personne responsable) {
        this.responsable = responsable;
    }

    public ApplicationIdent giveApplicationIdent() {
        return new ApplicationIdent(this.codeApplication, this.shortDescription);
    }

    public OrganizationIdent getOrganizationIdent() {
        return organizationIdent;
    }

    public void updateOrganization(OrganizationIdent organizationIdent) {
        this.organizationIdent = organizationIdent;
    }

    public void updateCycleLife(CycleLife cycleLife) { this.cycleLife = cycleLife;
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

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    public void deleteNote(String noteTitle) {

        for (Note note : notes.values()) {
            if (note.getNoteTitle().equals(noteTitle)) {
                notes.remove(getKey(notes, note));
            }
        }
    }

    public static class Builder {
        private final String codeApplication;
        private String shortDescription = "to be completed";
        private String longDescription = "to be completed";
        private Personne responsable = null;
        private OrganizationIdent organizationIdent;
        private CycleLife cycleLife= null;

        public Builder(String codeApplication) {
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

        public Builder withOrganization(OrganizationIdent organizationIdent) {
            this.organizationIdent = organizationIdent;
            return this;
        }

        public Builder withCycleLife(CycleLife cycleLife) {
            this.cycleLife = cycleLife;
            return this;
        }

        public Application build() {
            Application application = new Application(this.codeApplication);
            application.shortDescription = this.shortDescription;
            application.longDescription = this.longDescription;
            Boolean isValide= true;
            String message="";
            if (this.responsable == null) {
                isValide = false;
                message = String.format("%s\n Responsable est obligatoire  ", message );
            }
            if (this.organizationIdent == null) {
                isValide = false;
                message = String.format("%s\n Organisation est obligatoire  ", message );
            }
            if (this.cycleLife == null) {
                isValide = false;
                message = String.format("%s\n Cycle de vie est obligatoire  ", message );
            }

            if (!isValide){
                throw new EntityIncorrect(String.format("%s\nPour l'application %s", message , this.codeApplication));
            }

            application.responsable = this.responsable;
            application.organizationIdent = this.organizationIdent;
            application.cycleLife = this.cycleLife;
            log.trace("New Application Create");
            return application;
        }
    }
}
