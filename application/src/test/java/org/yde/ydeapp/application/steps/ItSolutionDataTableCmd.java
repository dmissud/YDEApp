package org.yde.ydeapp.application.steps;

public class ItSolutionDataTableCmd {
    private final String typeOfSolution;
    private final String nameOfFirmware;
    private final String labelOfSourcingMode;

    public ItSolutionDataTableCmd(String typeOfSolution, String nameOfFirmware, String labelOfSourcingMode) {
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

