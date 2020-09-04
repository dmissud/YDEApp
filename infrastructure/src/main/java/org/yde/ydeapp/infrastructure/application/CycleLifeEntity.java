package org.yde.ydeapp.infrastructure.application;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class CycleLifeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String state;
    private LocalDate dateOfCreation;
    private LocalDate dateOfLastUpdate;
    private LocalDate dateEndInReality;

    public long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public LocalDate getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public LocalDate getDateEndInReality() {
        return dateEndInReality;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setDateOfLastUpdate(LocalDate dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public void setDateEndInReality(LocalDate dateEndInReality) {
        this.dateEndInReality = dateEndInReality;
    }
}
