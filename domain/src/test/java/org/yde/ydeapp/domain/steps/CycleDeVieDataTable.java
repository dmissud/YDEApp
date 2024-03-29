package org.yde.ydeapp.domain.steps;

public class CycleDeVieDataTable {
    private final String dateOfCreation;
    private final String dateOfLastUpdate;
    private final String dateEndInReality;

    private final String state;


    public CycleDeVieDataTable(String state,
                               String dateOfCreation,
                               String dateOfLastUpdate,
                               String dateEndInReality) {
        this.state = state;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastUpdate = dateOfLastUpdate;
        this.dateEndInReality = dateEndInReality;
    }


    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public String getDateEndInReality() {
        return dateEndInReality;
    }
    public String getState() {
        return state;
    }

}
