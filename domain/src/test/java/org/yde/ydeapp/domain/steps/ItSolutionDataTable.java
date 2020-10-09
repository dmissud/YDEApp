package org.yde.ydeapp.domain.steps;



public class ItSolutionDataTable {
    private final String typeOfSolution;
    private final String nameOfFirmware;
    private final String labelOfSourcingMode;

    public ItSolutionDataTable(String typeOfSolution, String nameOfFirmware, String labelOfSourcingMode) {
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
