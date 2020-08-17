package org.yde.ydeapp.interfacerefi;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class ApplicationSourcePosition {
    @CsvBindByPosition(position = 0)
    private String identifiant;

    @CsvBindByPosition(position = 1)
    private String codeApp;

    @CsvBindByPosition(position = 2)
    private String shortLibelle;

    @CsvBindByPosition(position = 3)
    private String longLibelle;

    @CsvBindByPosition(position = 4)
    private String description;

    @CsvBindByPosition(position = 5)
    private String codeOfTypeOfApplication;

    @CsvBindByPosition(position = 6)
    private String libelleTypeApplication;

    @CsvBindByPosition(position = 7)
    private String state;

    @CsvBindByPosition(position = 8)
    @CsvDate("dd/MM/yyyy")
    private Date dateOfCreation;

    @CsvBindByPosition(position = 9)
    @CsvDate("dd/MM/yyyy")
    private Date dateOfLastUpdate;

    @CsvBindByPosition(position = 10)
    @CsvDate("dd/MM/yyyy")
    private Date dateEndProjected;

    @CsvBindByPosition(position = 11)
    private String applicationTarget;

    @CsvBindByPosition(position = 12)
    @CsvDate("dd/MM/yyyy")
    private Date dateEndInReality;

    @CsvBindByPosition(position = 13)
    @CsvDate("dd/MM/yyyy")
    private Date dateBeginningExploitation;

    @CsvBindByPosition(position = 14)
    @CsvDate("dd/MM/yyyy")
    private Date dateEndExploitation;

    @CsvBindByPosition(position = 15)
    private String idRefogEntityOwner;

    @CsvBindByPosition(position = 16)
    private String codeEntityOwner;

    @CsvBindByPosition(position = 17)
    private String labelEntityOwner;

    @CsvBindByPosition(position = 18)
    private String idRefogEntityMOA;

    @CsvBindByPosition(position = 19)
    private String codeEntityMOA;

    @CsvBindByPosition(position = 20)
    private String labelEntityMOA;

    @CsvBindByPosition(position = 21)
    private String idResponsableMOE;

    @CsvBindByPosition(position = 22)
    private String lastNameResponsableMoe;

    @CsvBindByPosition(position = 23)
    private String firstNameResponsableMoe;

    @CsvBindByPosition(position = 24)
    private String codeEntityResponsableMoe;

    @CsvBindByPosition(position = 25)
    private String labelEntityResponsableMoe;

    @CsvBindByPosition(position = 26)
    private String idRefogEntityMoe;

    @CsvBindByPosition(position = 27)
    private String codeEntityMoe;

    @CsvBindByPosition(position = 28)
    private String labelEntityMoe;

    @CsvBindByPosition(position = 29)
    private String idRefogEntityProduction;

    @CsvBindByPosition(position = 30)
    private String codeEntityProduction;

    @CsvBindByPosition(position = 31)
    private String labelEntityProduction;

    @CsvBindByPosition(position = 32)
    private String codeAttachmentUnderSector;

    @CsvBindByPosition(position = 33)
    private String labelAttachmentUnderSector;

    @CsvBindByPosition(position = 34)
    private String codeAttachmentSector;

    @CsvBindByPosition(position = 35)
    private String labelAttachmentSector;

    @CsvBindByPosition(position = 36)
    private String codeAttachmentPatrimony;

    @CsvBindByPosition(position = 37)
    private String labelAttachmentPatrimony;

    @CsvBindByPosition(position = 38)
    private String codeAttachmentDPP;

    @CsvBindByPosition(position = 39)
    private String labelAttachmentDPP;

    @CsvBindByPosition(position = 40)
    private String labelFunctionalCluster;

    @CsvBindByPosition(position = 41)
    private String labelOfSourcingMode;

    @CsvBindByPosition(position = 42)
    private String privilegeInformation;

    @CsvBindByPosition(position = 43)
    private String personalData;

    @CsvBindByPosition(position = 44)
    private String serviceClass;

    @CsvBindByPosition(position = 45)
    private String availability;

    @CsvBindByPosition(position = 46)
    private String RPO;

    @CsvBindByPosition(position = 47)
    private String RTO;

    @CsvBindByPosition(position = 48)
    private String integrity;

    @CsvBindByPosition(position = 49)
    private String privacy;

    @CsvBindByPosition(position = 50)
    private String traceability;

    @CsvBindByPosition(position = 51)
    private String businessCriticality;

    @CsvBindByPosition(position = 52)
    private String continuityLevel;

    @CsvBindByPosition(position = 53)
    private String derogation;

    @CsvBindByPosition(position = 54)
    @CsvDate("dd/MM/yyyy")
    private Date dateOfValidationOfServiceDefinition;

    @CsvBindByPosition(position = 55)
    private String typeOfSolution;

    @CsvBindByPosition(position = 56)
    private String nameOfFirmware;

    @CsvBindByPosition(position = 57)
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

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public void setDateOfLastUpdate(Date dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public Date getDateEndProjected() {
        return dateEndProjected;
    }

    public void setDateEndProjected(Date dateEndProjected) {
        this.dateEndProjected = dateEndProjected;
    }

    public String getApplicationTarget() {
        return applicationTarget;
    }

    public void setApplicationTarget(String applicationTarget) {
        this.applicationTarget = applicationTarget;
    }

    public Date getDateEndInReality() {
        return dateEndInReality;
    }

    public void setDateEndInReality(Date dateEndInReality) {
        this.dateEndInReality = dateEndInReality;
    }

    public Date getDateBeginningExploitation() {
        return dateBeginningExploitation;
    }

    public void setDateBeginningExploitation(Date dateBeginningExploitation) {
        this.dateBeginningExploitation = dateBeginningExploitation;
    }

    public Date getDateEndExploitation() {
        return dateEndExploitation;
    }

    public void setDateEndExploitation(Date dateEndExploitation) {
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
        return RPO;
    }

    public void setRPO(String RPO) {
        this.RPO = RPO;
    }

    public String getRTO() {
        return RTO;
    }

    public void setRTO(String RTO) {
        this.RTO = RTO;
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

    public Date getDateOfValidationOfServiceDefinition() {
        return dateOfValidationOfServiceDefinition;
    }

    public void setDateOfValidationOfServiceDefinition(Date dateOfValidationOfServiceDefinition) {
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
