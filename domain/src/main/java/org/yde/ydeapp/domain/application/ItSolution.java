package org.yde.ydeapp.domain.application;

public class ItSolution {

    private String typeOfSolution;
    private String nameOfFirmware;
    private String labelOfSourcingMode;

    public ItSolution(String typeOfSolution, String nameOfFirmware, String labelOfSourcingMode) {
        this.typeOfSolution = typeOfSolution;
        this.nameOfFirmware = nameOfFirmware;
        this.labelOfSourcingMode = labelOfSourcingMode;
    }

    public String getTypeOfSolution() {
        return typeOfSolution;
    }

    public String getNameOfFirmware() {
        return nameOfFirmware;
    }

    public String getLabelOfSourcingMode() {
        return labelOfSourcingMode;
    }
}
