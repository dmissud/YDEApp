package org.yde.ydeapp.infrastructure.application;


import org.yde.ydeapp.infrastructure.organization.OrganizationEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codeApp;

    private String shortDescription;

    private String longDescription;

    @Embedded
    private PersonneEntity responsable;

    @ManyToOne(cascade = CascadeType.ALL)
    private OrganizationEntity organisation;

    @Embedded
    private CycleLifeEntity cycleLife;

    @Embedded
    private ItSolutionEntity itSolution;

    @Embedded
    private CriticityEntity criticity;

    @ElementCollection
    private List<NoteEntity> notes;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeApp() { return codeApp; }

    public void setCodeApp(String codeApp) {
        this.codeApp = codeApp;
    }

    public String getShortDescription() { return shortDescription; }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() { return longDescription; }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public PersonneEntity getResponsable() {
        return responsable;
    }

    public void setResponsable(PersonneEntity responsable) {
        this.responsable = responsable;
    }

    public OrganizationEntity getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganizationEntity organisation) {
        this.organisation = organisation;
    }

    public CycleLifeEntity getCycleLife() {
        return cycleLife;
    }

    public void setCycleLife(CycleLifeEntity cycleLife) {
        this.cycleLife = cycleLife;
    }

    public ItSolutionEntity getItSolution() {
        return itSolution;
    }

    public void setItSolution(ItSolutionEntity itSolution) {
        this.itSolution = itSolution;
    }

    public CriticityEntity getCriticity() {
        return criticity;
    }

    public void setCriticity(CriticityEntity criticity) {
        this.criticity = criticity;
    }

    public List<NoteEntity> getNotes() { return notes; }

    public void setNotes(List<NoteEntity> notes) { this.notes = notes; }


}
