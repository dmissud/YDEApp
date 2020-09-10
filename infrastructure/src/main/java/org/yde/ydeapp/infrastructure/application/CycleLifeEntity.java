package org.yde.ydeapp.infrastructure.application;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class CycleLifeEntity {
    private String state;
    private LocalDate dateOfCreation;
    private LocalDate dateOfLastUpdate;
    private LocalDate dateEndInReality;

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
