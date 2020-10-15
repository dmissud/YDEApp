package org.yde.ydeapp.interfacerefi;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ApplicationSourcePosition {
    private String identifiant;
    private String codeApp;
    private String shortLibelle;
    private String longLibelle;
    private String description;
    private String codeOfTypeOfApplication;
    private String libelleTypeApplication;
    private String state;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    private LocalDate dateOfCreation;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    private LocalDate dateOfLastUpdate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    private LocalDate dateEndProjected;
    private String applicationTarget;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    private LocalDate dateEndInReality;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    private LocalDate dateBeginningExploitation;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    private LocalDate dateEndExploitation;
    private String idRefogEntityOwner;
    private String codeEntityOwner;
    private String labelEntityOwner;
    private String idRefogEntityMOA;
    private String codeEntityMOA;
    private String labelEntityMOA;
    private String idResponsableMOE;
    private String lastNameResponsableMoe;
    private String firstNameResponsableMoe;
    private String codeEntityResponsableMoe;
    private String labelEntityResponsableMoe;
    private String idRefogEntityMoe;
    private String codeEntityMoe;
    private String labelEntityMoe;
    private String idRefogEntityProduction;
    private String codeEntityProduction;
    private String labelEntityProduction;
    private String codeAttachmentUnderSector;
    private String labelAttachmentUnderSector;
    private String codeAttachmentSector;
    private String labelAttachmentSector;
    private String codeAttachmentPatrimony;
    private String labelAttachmentPatrimony;
    private String codeAttachmentDPP;
    private String labelAttachmentDPP;
    private String labelFunctionalCluster;
    private String labelOfSourcingMode;
    private String privilegeInformation;
    private String personalData;
    private String serviceClass;
    private String availability;
    private String rPO;
    private String rTO;
    private String integrity;
    private String privacy;
    private String traceability;
    private String businessCriticality;
    private String continuityLevel;
    private String derogation;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    private LocalDate dateOfValidationOfServiceDefinition;
    private String typeOfSolution;
    private String nameOfFirmware;
    private String technicalApplication;

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getCodeApp() {
        return codeApp;
    }

    public void setCodeApp(String codeApp) {
        this.codeApp = codeApp;
    }

    public String getShortLibelle() {
        return shortLibelle;
    }

    public void setShortLibelle(String shortLibelle) {
        this.shortLibelle = shortLibelle;
    }

    public String getLongLibelle() {
        return longLibelle;
    }

    public void setLongLibelle(String longLibelle) {
        this.longLibelle = longLibelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeOfTypeOfApplication() {
        return codeOfTypeOfApplication;
    }

    public void setCodeOfTypeOfApplication(String codeOfTypeOfApplication) {
        this.codeOfTypeOfApplication = codeOfTypeOfApplication;
    }

    public String getLibelleTypeApplication() {
        return libelleTypeApplication;
    }

    public void setLibelleTypeApplication(String libelleTypeApplication) {
        this.libelleTypeApplication = libelleTypeApplication;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDate getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public void setDateOfLastUpdate(LocalDate dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public LocalDate getDateEndProjected() {
        return dateEndProjected;
    }

    public void setDateEndProjected(LocalDate dateEndProjected) {
        this.dateEndProjected = dateEndProjected;
    }

    public String getApplicationTarget() {
        return applicationTarget;
    }

    public void setApplicationTarget(String applicationTarget) {
        this.applicationTarget = applicationTarget;
    }

    public LocalDate getDateEndInReality() {
        return dateEndInReality;
    }

    public void setDateEndInReality(LocalDate dateEndInReality) {
        this.dateEndInReality = dateEndInReality;
    }

    public LocalDate getDateBeginningExploitation() {
        return dateBeginningExploitation;
    }

    public void setDateBeginningExploitation(LocalDate dateBeginningExploitation) {
        this.dateBeginningExploitation = dateBeginningExploitation;
    }

    public LocalDate getDateEndExploitation() {
        return dateEndExploitation;
    }

    public void setDateEndExploitation(LocalDate dateEndExploitation) {
        this.dateEndExploitation = dateEndExploitation;
    }

    public String getIdRefogEntityOwner() {
        return idRefogEntityOwner;
    }

    public void setIdRefogEntityOwner(String idRefogEntityOwner) {
        this.idRefogEntityOwner = idRefogEntityOwner;
    }

    public String getCodeEntityOwner() {
        return codeEntityOwner;
    }

    public void setCodeEntityOwner(String codeEntityOwner) {
        this.codeEntityOwner = codeEntityOwner;
    }

    public String getLabelEntityOwner() {
        return labelEntityOwner;
    }

    public void setLabelEntityOwner(String labelEntityOwner) {
        this.labelEntityOwner = labelEntityOwner;
    }

    public String getIdRefogEntityMOA() {
        return idRefogEntityMOA;
    }

    public void setIdRefogEntityMOA(String idRefogEntityMOA) {
        this.idRefogEntityMOA = idRefogEntityMOA;
    }

    public String getCodeEntityMOA() {
        return codeEntityMOA;
    }

    public void setCodeEntityMOA(String codeEntityMOA) {
        this.codeEntityMOA = codeEntityMOA;
    }

    public String getLabelEntityMOA() {
        return labelEntityMOA;
    }

    public void setLabelEntityMOA(String labelEntityMOA) {
        this.labelEntityMOA = labelEntityMOA;
    }

    public String getIdResponsableMOE() {
        return idResponsableMOE;
    }

    public void setIdResponsableMOE(String idResponsableMOE) {
        this.idResponsableMOE = idResponsableMOE;
    }

    public String getFirstNameResponsableMoe() {
        return firstNameResponsableMoe;
    }

    public void setFirstNameResponsableMoe(String firstNameResponsableMoe) {
        this.firstNameResponsableMoe = firstNameResponsableMoe;
    }

    public String getLastNameResponsableMoe() {
        return lastNameResponsableMoe;
    }

    public void setLastNameResponsableMoe(String lastNameResponsableMoe) {
        this.lastNameResponsableMoe = lastNameResponsableMoe;
    }

    public String getCodeEntityResponsableMoe() {
        return codeEntityResponsableMoe;
    }

    public void setCodeEntityResponsableMoe(String codeEntityResponsableMoe) {
        this.codeEntityResponsableMoe = codeEntityResponsableMoe;
    }

    public String getLabelEntityResponsableMoe() {
        return labelEntityResponsableMoe;
    }

    public void setLabelEntityResponsableMoe(String labelEntityResponsableMoe) {
        this.labelEntityResponsableMoe = labelEntityResponsableMoe;
    }

    public String getIdRefogEntityMoe() {
        return idRefogEntityMoe;
    }

    public void setIdRefogEntityMoe(String idRefogEntityMoe) {
        this.idRefogEntityMoe = idRefogEntityMoe;
    }

    public String getCodeEntityMoe() {
        return codeEntityMoe;
    }

    public void setCodeEntityMoe(String codeEntityMoe) {
        this.codeEntityMoe = codeEntityMoe;
    }

    public String getLabelEntityMoe() {
        return labelEntityMoe;
    }

    public void setLabelEntityMoe(String labelEntityMoe) {
        this.labelEntityMoe = labelEntityMoe;
    }

    public String getIdRefogEntityProduction() {
        return idRefogEntityProduction;
    }

    public void setIdRefogEntityProduction(String idRefogEntityProduction) {
        this.idRefogEntityProduction = idRefogEntityProduction;
    }

    public String getCodeEntityProduction() {
        return codeEntityProduction;
    }

    public void setCodeEntityProduction(String codeEntityProduction) {
        this.codeEntityProduction = codeEntityProduction;
    }

    public String getLabelEntityProduction() {
        return labelEntityProduction;
    }

    public void setLabelEntityProduction(String labelEntityProduction) {
        this.labelEntityProduction = labelEntityProduction;
    }

    public String getCodeAttachmentUnderSector() {
        return codeAttachmentUnderSector;
    }

    public void setCodeAttachmentUnderSector(String codeAttachmentUnderSector) {
        this.codeAttachmentUnderSector = codeAttachmentUnderSector;
    }

    public String getLabelAttachmentUnderSector() {
        return labelAttachmentUnderSector;
    }

    public void setLabelAttachmentUnderSector(String labelAttachmentUnderSector) {
        this.labelAttachmentUnderSector = labelAttachmentUnderSector;
    }

    public String getCodeAttachmentSector() {
        return codeAttachmentSector;
    }

    public void setCodeAttachmentSector(String codeAttachmentSector) {
        this.codeAttachmentSector = codeAttachmentSector;
    }

    public String getLabelAttachmentSector() {
        return labelAttachmentSector;
    }

    public void setLabelAttachmentSector(String labelAttachmentSector) {
        this.labelAttachmentSector = labelAttachmentSector;
    }

    public String getCodeAttachmentPatrimony() {
        return codeAttachmentPatrimony;
    }

    public void setCodeAttachmentPatrimony(String codeAttachmentPatrimony) {
        this.codeAttachmentPatrimony = codeAttachmentPatrimony;
    }

    public String getLabelAttachmentPatrimony() {
        return labelAttachmentPatrimony;
    }

    public void setLabelAttachmentPatrimony(String labelAttachmentPatrimony) {
        this.labelAttachmentPatrimony = labelAttachmentPatrimony;
    }

    public String getCodeAttachmentDPP() {
        return codeAttachmentDPP;
    }

    public void setCodeAttachmentDPP(String codeAttachmentDPP) {
        this.codeAttachmentDPP = codeAttachmentDPP;
    }

    public String getLabelAttachmentDPP() {
        return labelAttachmentDPP;
    }

    public void setLabelAttachmentDPP(String labelAttachmentDPP) {
        this.labelAttachmentDPP = labelAttachmentDPP;
    }

    public String getLabelFunctionalCluster() {
        return labelFunctionalCluster;
    }

    public void setLabelFunctionalCluster(String labelFunctionalCluster) {
        this.labelFunctionalCluster = labelFunctionalCluster;
    }

    public String getLabelOfSourcingMode() {
        return labelOfSourcingMode;
    }

    public void setLabelOfSourcingMode(String labelOfSourcingMode) {
        this.labelOfSourcingMode = labelOfSourcingMode;
    }

    public String getPrivilegeInformation() {
        return privilegeInformation;
    }

    public void setPrivilegeInformation(String privilegeInformation) {
        this.privilegeInformation = privilegeInformation;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getRPO() {
        return rPO;
    }

    public void setRPO(String rPO) {
        this.rPO = rPO;
    }

    public String getRTO() {
        return rTO;
    }

    public void setRTO(String rTO) {
        this.rTO = rTO;
    }

    public String getIntegrity() {
        return integrity;
    }

    public void setIntegrity(String integrity) {
        this.integrity = integrity;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getTraceability() {
        return traceability;
    }

    public void setTraceability(String traceability) {
        this.traceability = traceability;
    }

    public String getBusinessCriticality() {
        return businessCriticality;
    }

    public void setBusinessCriticality(String businessCriticality) {
        this.businessCriticality = businessCriticality;
    }

    public String getContinuityLevel() {
        return continuityLevel;
    }

    public void setContinuityLevel(String continuityLevel) {
        this.continuityLevel = continuityLevel;
    }

    public String getDerogation() {
        return derogation;
    }

    public void setDerogation(String derogation) {
        this.derogation = derogation;
    }

    public LocalDate getDateOfValidationOfServiceDefinition() {
        return dateOfValidationOfServiceDefinition;
    }

    public void setDateOfValidationOfServiceDefinition(LocalDate dateOfValidationOfServiceDefinition) {
        this.dateOfValidationOfServiceDefinition = dateOfValidationOfServiceDefinition;
    }

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

    public String getTechnicalApplication() {
        return technicalApplication;
    }

    public void setTechnicalApplication(String technicalApplication) {
        this.technicalApplication = technicalApplication;
    }
}
