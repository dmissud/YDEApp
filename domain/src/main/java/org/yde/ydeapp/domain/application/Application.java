package org.yde.ydeapp.domain.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.domain.out.EntityIncorrect;

import java.time.LocalDate;
import java.util.Collection;
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
    private ItSolution itSolution;
    private Criticity criticity;
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
    public ItSolution getItSolution() {
        return itSolution;
    }

    public Collection<Note> getNotes() {
        return notes.values();
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

    public Criticity getCriticity() {
        return criticity;
    }

    public OrganizationIdent getOrganizationIdent() {
        return organizationIdent;
    }

    public void updateOrganization(OrganizationIdent organizationIdent) {
        this.organizationIdent = organizationIdent;
    }

    public void updateCycleLife(CycleLife cycleLife) { this.cycleLife = cycleLife;
    }
    public void updateItSolution(ItSolution itSolution) { this.itSolution = itSolution;
    }
    public void updateCriticity(Criticity criticity){this.criticity=criticity;
    }

    public Map<String, Note> retrieveNotes() {
        return Collections.unmodifiableMap(notes);
    }

    public Note retrieveNoteByTitle(String noteTitle) {
        return notes.get(noteTitle);
    }




    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(map.get(entry.getKey()))) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void deleteNote(String noteTitle) {

        for (Note note : notes.values()) {
            if (note.getNoteTitle().equals(noteTitle)) {
                notes.remove(getKey(notes, note));
                break;
            }
        }
    }

    public Note storeOfNote(String noteTitle, String noteContent) {
        Note note = notes.get(noteTitle);
        if ( note == null) {

            notes.put(noteTitle, new Note(noteTitle,noteContent, LocalDate.now()));
        } else {
            notes.replace(noteTitle, new Note(noteTitle,noteContent, note.getNoteCreationDate()));
        }
        return notes.get(noteTitle);
    }

    public void storeOfNote(Note newNote) {
        Note note = notes.get(newNote.getNoteTitle());
        if ( note == null) {

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
        private OrganizationIdent organizationIdent;
        private CycleLife cycleLife= null;
        private ItSolution itSolution=null;
        private Criticity criticity=null;

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
        public Builder withItSolution(ItSolution itSolution) {
            this.itSolution = itSolution;
            return this;
        }
        public Builder withCriticity(Criticity criticity) {
            this.criticity = criticity;
            return this;
        }

        public Application build() {
            Application application = new Application(this.codeApplication);
            application.shortDescription = this.shortDescription;
            application.longDescription = this.longDescription;
            boolean isValide= true;
            String message="";
            if (this.responsable == null) {
                isValide = false;
                message = String.format("%s%n Responsable est obligatoire  ", message );
            }
            if (this.organizationIdent == null) {
                isValide = false;
                message = String.format("%s%n Organisation est obligatoire  ", message );
            }
            if (this.cycleLife == null) {
                isValide = false;
                message = String.format("%s%n Cycle de vie est obligatoire  ", message );
            }
            if (this.itSolution == null) {
                isValide = false;
                message = String.format("%s\n la solution It est obligatoire  ", message );
            }
            if (this.criticity == null) {
                isValide = false;
                message = String.format("%s\n la criticité est obligatoire  ", message );
            }

            if (!isValide){
                throw new EntityIncorrect(String.format("%s%nPour l'application %s", message , this.codeApplication));
            }

            application.responsable = this.responsable;
            application.organizationIdent = this.organizationIdent;
            application.cycleLife = this.cycleLife;
            application.itSolution= this.itSolution;
            application.criticity=this.criticity;
            log.trace("New Application Create");
            return application;
        }
    }
}
