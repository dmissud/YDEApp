package org.yde.ydeapp.infrastructure.application;

import javax.persistence.*;

@Entity
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codeApp;

    private String shortDescription;

    private String longDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    private PersonneEntity responsable;

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
}
