package org.yde.ydeapp.domain;

import java.util.Date;

public class CycleLife {
    private String state;
    private Date dateOfCreation;
    private Date dateOfLastUpdate;
    private Date dateEndInReality;

    public CycleLife(String state, Date dateOfCreation, Date dateOfLastUpdate, Date dateEndInReality) {
        this.state = state;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastUpdate = dateOfLastUpdate;
        this.dateEndInReality = dateEndInReality;
    }

    public String getState() {
        return state;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Date getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public Date getDateEndInReality() {
        return dateEndInReality;
    }
}
