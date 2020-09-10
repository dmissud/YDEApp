package org.yde.ydeapp.domain;

import java.time.LocalDate;

public class CycleLife {
    private final String state;
    private final LocalDate dateOfCreation;
    private final LocalDate dateOfLastUpdate;
    private final LocalDate dateEndInReality;

    public CycleLife(String state, LocalDate dateOfCreation, LocalDate dateOfLastUpdate, LocalDate dateEndInReality) {
        this.state = state;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastUpdate = dateOfLastUpdate;
        this.dateEndInReality = dateEndInReality;
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
}
