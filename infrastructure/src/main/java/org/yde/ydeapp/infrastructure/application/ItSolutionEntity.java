package org.yde.ydeapp.infrastructure.application;

import javax.persistence.Embeddable;

@Embeddable
public class ItSolutionEntity {
    private String typeOfSolution;
    private String nameOfFirmware;
    private String labelOfSourcingMode;

    public String getTypeOfSolution() {
        return typeOfSolution;
    }

    public void setTypeOfSolution(String typeOfSolution) {
        this.typeOfSolution = typeOfSolution;
    }

    public String getNameOfFirmware() {
        return nameOfFirmware;
    }

    public void setNameOfFirmware(String nameOfFirmware) {
        this.nameOfFirmware = nameOfFirmware;
    }

    public String getLabelOfSourcingMode() {
        return labelOfSourcingMode;
    }

    public void setLabelOfSourcingMode(String labelOfSourcingMode) {
        this.labelOfSourcingMode = labelOfSourcingMode;
    }
}
