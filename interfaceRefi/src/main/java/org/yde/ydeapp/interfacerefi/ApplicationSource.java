package org.yde.ydeapp.interfacerefi;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class ApplicationSource {
    @CsvBindByName(column = "Identifiant", format = "ISO_8859_1")
    private String identifiant;
    @CsvBindByName(column = "Code AP", format = "ISO_8859_1")
    private String codeApp;
    @CsvBindByName(column = "Libellé court", format = "ISO_8859_1")
    private String shortDescription;
    @CsvBindByName(column = "Libellé long", format = "ISO_8859_1")
    private String longDescription;
    @CsvBindByName(column = "Code du type d'application")
    private String codeOfType;
    @CsvBindByName(column = "Libellé Type Application")
    private String descriptionTypeApplication ;
    @CsvBindByName(column = "Etat")
    private String state;
    @CsvBindByName(column = "Date de création de l'identifiant")
    @CsvDate("dd/MM/yyyy")
    private Date dateOfCreation;
    @CsvBindByName(column = "Date de dernière mise à jour")
    @CsvDate("dd/MM/yyyy")
    private Date dateOfLastUpdate;
    @CsvBindByName(column = "Date de fin prévisionnelle")
    @CsvDate("dd/MM/yyyy")
    private Date dateEndProjected;
    @CsvBindByName(column = "Application cible")
    private String applicationTaget;
    @CsvBindByName(column = "Date de fin réelle")
    @CsvDate("dd/MM/yyyy")
    private Date dateEndInReality;
    @CsvBindByName(column = "Date de début d'exploitation")
    @CsvDate("dd/MM/yyyy")
    private Date dateBeginningExploitation;
    @CsvBindByName(column = "Date de fin d'exploitation")
    @CsvDate("dd/MM/yyyy")
    private Date dateEndExploitation;
    @CsvBindByName(column = "Identifiant REFOG Entité propriétaire")
    private String idRefogEntityOwner;
    @CsvBindByName(column = "Code entité propriétaire")
    private String codeEntityOwner;
    @CsvBindByName(column = "Libellé entité propriétaire")
    private String labelEntityOwner;
    @CsvBindByName(column = "Identifiant REFOG Entité MOA")
    private String idRefogEntityMOA;
    @CsvBindByName(column = "Code entité MOA")
    private String codeEntityMOA;
    @CsvBindByName(column = "Libellé entité MOA")
    private String labelEntityMOA;
    @CsvBindByName(column = "Identifiant annuaire du Responsable MOE")
    private String idResponsableMOE;
    @CsvBindByName(column = "Nom du Responsable MOE")
    private String firstNameResponsableMoe;
    @CsvBindByName(column = "Prénom du Responsable MOE")
    private String lastNameResponsableMoe;
    @CsvBindByName(column = "Code de l'entité du Responsable MOE")
    private String codeEntityResponsableMoe;
    @CsvBindByName(column = "Libellé de l'entité du Responsable MOE")
    private String labelEntityResponsableMoe;
    @CsvBindByName(column = "Identifiant Refog de l'entité MOE")
    private String idRefogEntityMoe;
    @CsvBindByName(column = "Code de l'entité MOE")
    private String codeEntityMoe;
    @CsvBindByName(column = "Libellé de l'entité MOE")
    private String labelEntityMoe;
    @CsvBindByName(column = "Identifiant Refog de l'entité de production")
    private String idRefogEntityProduction;
    @CsvBindByName(column = "Code de l'entité de production")
    private String codeEntityProduction;
    @CsvBindByName(column = "Libellé de l'entité de production")
    private String labelEntityProduction;
    @CsvBindByName(column = "Code Sous-Filière de rattachement")
    private String codeAttachmentUnderSector;
    @CsvBindByName(column = "Libellé Sous-Filière de rattachement")
    private String labelAttachmentUnderSector;
    @CsvBindByName(column = "Code Filiere de rattachement")
    private String codeAttachmentSector;
    @CsvBindByName(column = "Libellé Filiere de rattachement")
    private String labelAttachmentSector;
    @CsvBindByName(column = "Code du Patrimoine de rattachement")
    private String codeAttachmentPatrimony;
    @CsvBindByName(column = "Libellé du patrimoine de rattachement")
    private String labelAttachmentPatrimony;
    @CsvBindByName(column = "Code DPP de rattachement")
    private String codeAttachmentDPP;
    @CsvBindByName(column = "Libellé DPP de rattachement")
    private String labelAttachmentDPP;
    @CsvBindByName(column = "Libellé regroupement fonctionnel")
    private String labelFunctionalCluster;
    @CsvBindByName(column = "Libellé du Mode de Sourcing")
    private String labelOfSourcingMode;
    @CsvBindByName(column = "Information Privilégiée")
    private String privilegeInformation;
    @CsvBindByName(column = "Données personnelles")
    private String personalData;
    @CsvBindByName(column = "Classe de service")
    private String serviceClass;
    @CsvBindByName(column = "Disponibilité")
    private String availability;
    @CsvBindByName(column = "RPO")
    private String RPO;
    @CsvBindByName(column = "RTO")
    private String RTO;
    @CsvBindByName(column = "Intégrité")
    private String integrity;
    @CsvBindByName(column = "Confidentialité")
    private String privacy;
    @CsvBindByName(column = "Traçabilité")
    private String traceability;
    @CsvBindByName(column = "Criticité métier")
    private String businessCriticality;
    @CsvBindByName(column = "Continuity Level")
    private String continuityLevel;
    @CsvBindByName(column = "Dérogation")
    private String derogation;
    @CsvBindByName(column = "Date de validation de la définition de service")
    private String dateOfValidationOfServiceDefinition;
    @CsvBindByName(column = "Type de solution")
    private String typeOfSolution;
    @CsvBindByName(column = "Nom du progiciel")
    private String nameOfFirmware;
    @CsvBindByName(column = "Applications Techniques")
    private String technicalApplication;

    public ApplicationSource(String detail, String applicationDetail, String identifiant, String codeApp, String shortDescription, String longDescription, String codeOfType,
                             String descriptionTypeApplication, String state, Date dateOfCreation, Date dateOfLastUpdate,
                             Date dateEndProjected, String applicationTaget, Date dateEndInReality, Date dateBeginningExploitation, Date dateEndExploitation,
                             String idRefogEntityOwner, String codeEntityOwner, String labelEntityOwner, String idRefogEntityMOA,
                             String codeEntityMOA, String labelEntityMOA, String idResponsableMOE, String firstNameResponsableMoe,
                             String lastNameResponsableMoe, String codeEntityResponsableMoe, String labelEntityResponsableMoe,
                             String idRefogEntityMoe, String codeEntityMoe, String labelEntityMoe, String idRefogEntityProduction,
                             String codeEntityProduction, String labelEntityProduction, String codeAttachmentUnderSector,
                             String labelAttachmentUnderSector, String codeAttachmentSector, String labelAttachmentSector,
                             String codeAttachmentPatrimony, String labelAttachmentPatrimony, String codeAttachmentDPP,
                             String labelAttachmentDPP, String labelFunctionalCluster, String labelOfSourcingMode,
                             String privilegeInformation, String personalData, String serviceClass, String availability,
                             String RPO, String RTO, String integrity, String privacy, String traceability, String businessCriticality,
                             String continuityLevel, String derogation, String dateOfValidationOfServiceDefinition, String typeOfSolution,
                             String nameOfFirmware, String technicalApplication) {
        this.identifiant = identifiant;
        this.codeApp = codeApp;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.codeOfType = codeOfType;
        this.descriptionTypeApplication = descriptionTypeApplication;
        this.state = state;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastUpdate = dateOfLastUpdate;
        this.dateEndProjected = dateEndProjected;
        this.applicationTaget = applicationTaget;
        this.dateEndInReality = dateEndInReality;
        this.dateBeginningExploitation = dateBeginningExploitation;
        this.dateEndExploitation = dateEndExploitation;
        this.idRefogEntityOwner = idRefogEntityOwner;
        this.codeEntityOwner = codeEntityOwner;
        this.labelEntityOwner = labelEntityOwner;
        this.idRefogEntityMOA = idRefogEntityMOA;
        this.codeEntityMOA = codeEntityMOA;
        this.labelEntityMOA = labelEntityMOA;
        this.idResponsableMOE = idResponsableMOE;
        this.firstNameResponsableMoe = firstNameResponsableMoe;
        this.lastNameResponsableMoe = lastNameResponsableMoe;
        this.codeEntityResponsableMoe = codeEntityResponsableMoe;
        this.labelEntityResponsableMoe = labelEntityResponsableMoe;
        this.idRefogEntityMoe = idRefogEntityMoe;
        this.codeEntityMoe = codeEntityMoe;
        this.labelEntityMoe = labelEntityMoe;
        this.idRefogEntityProduction = idRefogEntityProduction;
        this.codeEntityProduction = codeEntityProduction;
        this.labelEntityProduction = labelEntityProduction;
        this.codeAttachmentUnderSector = codeAttachmentUnderSector;
        this.labelAttachmentUnderSector = labelAttachmentUnderSector;
        this.codeAttachmentSector = codeAttachmentSector;
        this.labelAttachmentSector = labelAttachmentSector;
        this.codeAttachmentPatrimony = codeAttachmentPatrimony;
        this.labelAttachmentPatrimony = labelAttachmentPatrimony;
        this.codeAttachmentDPP = codeAttachmentDPP;
        this.labelAttachmentDPP = labelAttachmentDPP;
        this.labelFunctionalCluster = labelFunctionalCluster;
        this.labelOfSourcingMode = labelOfSourcingMode;
        this.privilegeInformation = privilegeInformation;
        this.personalData = personalData;
        this.serviceClass = serviceClass;
        this.availability = availability;
        this.RPO = RPO;
        this.RTO = RTO;
        this.integrity = integrity;
        this.privacy = privacy;
        this.traceability = traceability;
        this.businessCriticality = businessCriticality;
        this.continuityLevel = continuityLevel;
        this.derogation = derogation;
        this.dateOfValidationOfServiceDefinition = dateOfValidationOfServiceDefinition;
        this.typeOfSolution = typeOfSolution;
        this.nameOfFirmware = nameOfFirmware;
        this.technicalApplication = technicalApplication;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getCodeApp() {
        return codeApp;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getCodeOfType() {
        return codeOfType;
    }

    public String getDescriptionTypeApplication() {
        return descriptionTypeApplication;
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

    public Date getDateEndProjected() {
        return dateEndProjected;
    }

    public String getApplicationTaget() {
        return applicationTaget;
    }

    public Date getDateEndInReality() {
        return dateEndInReality;
    }

    public Date getDateBeginningExploitation() {
        return dateBeginningExploitation;
    }

    public Date getDateEndExploitation() {
        return dateEndExploitation;
    }

    public String getIdRefogEntityOwner() {
        return idRefogEntityOwner;
    }

    public String getCodeEntityOwner() {
        return codeEntityOwner;
    }

    public String getLabelEntityOwner() {
        return labelEntityOwner;
    }

    public String getIdRefogEntityMOA() {
        return idRefogEntityMOA;
    }

    public String getCodeEntityMOA() {
        return codeEntityMOA;
    }

    public String getLabelEntityMOA() {
        return labelEntityMOA;
    }

    public String getIdResponsableMOE() {
        return idResponsableMOE;
    }

    public String getFirstNameResponsableMoe() {
        return firstNameResponsableMoe;
    }

    public String getLastNameResponsableMoe() {
        return lastNameResponsableMoe;
    }

    public String getCodeEntityResponsableMoe() {
        return codeEntityResponsableMoe;
    }

    public String getLabelEntityResponsableMoe() {
        return labelEntityResponsableMoe;
    }

    public String getIdRefogEntityMoe() {
        return idRefogEntityMoe;
    }

    public String getCodeEntityMoe() {
        return codeEntityMoe;
    }

    public String getLabelEntityMoe() {
        return labelEntityMoe;
    }

    public String getIdRefogEntityProduction() {
        return idRefogEntityProduction;
    }

    public String getCodeEntityProduction() {
        return codeEntityProduction;
    }

    public String getLabelEntityProduction() {
        return labelEntityProduction;
    }

    public String getCodeAttachmentUnderSector() {
        return codeAttachmentUnderSector;
    }

    public String getLabelAttachmentUnderSector() {
        return labelAttachmentUnderSector;
    }

    public String getCodeAttachmentSector() {
        return codeAttachmentSector;
    }

    public String getLabelAttachmentSector() {
        return labelAttachmentSector;
    }

    public String getCodeAttachmentPatrimony() {
        return codeAttachmentPatrimony;
    }

    public String getLabelAttachmentPatrimony() {
        return labelAttachmentPatrimony;
    }

    public String getCodeAttachmentDPP() {
        return codeAttachmentDPP;
    }

    public String getLabelAttachmentDPP() {
        return labelAttachmentDPP;
    }

    public String getLabelFunctionalCluster() {
        return labelFunctionalCluster;
    }

    public String getLabelOfSourcingMode() {
        return labelOfSourcingMode;
    }

    public String getPrivilegeInformation() {
        return privilegeInformation;
    }

    public String getPersonalData() {
        return personalData;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public String getAvailability() {
        return availability;
    }

    public String getRPO() {
        return RPO;
    }

    public String getRTO() {
        return RTO;
    }

    public String getIntegrity() {
        return integrity;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getTraceability() {
        return traceability;
    }

    public String getBusinessCriticality() {
        return businessCriticality;
    }

    public String getContinuityLevel() {
        return continuityLevel;
    }

    public String getDerogation() {
        return derogation;
    }

    public String getDateOfValidationOfServiceDefinition() {
        return dateOfValidationOfServiceDefinition;
    }

    public String getTypeOfSolution() {
        return typeOfSolution;
    }

    public String getNameOfFirmware() {
        return nameOfFirmware;
    }

    public String getTechnicalApplication() {
        return technicalApplication;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setCodeApp(String codeApp) {
        this.codeApp = codeApp;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setCodeOfType(String codeOfType) {
        this.codeOfType = codeOfType;
    }

    public void setDescriptionTypeApplication(String descriptionTypeApplication) {
        this.descriptionTypeApplication = descriptionTypeApplication;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setDateOfLastUpdate(Date dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public void setDateEndProjected(Date dateEndProjected) {
        this.dateEndProjected = dateEndProjected;
    }

    public void setApplicationTaget(String applicationTaget) {
        this.applicationTaget = applicationTaget;
    }

    public void setDateEndInReality(Date dateEndInReality) {
        this.dateEndInReality = dateEndInReality;
    }

    public void setDateBeginningExploitation(Date dateBeginningExploitation) {
        this.dateBeginningExploitation = dateBeginningExploitation;
    }

    public void setDateEndExploitation(Date dateEndExploitation) {
        this.dateEndExploitation = dateEndExploitation;
    }

    public void setIdRefogEntityOwner(String idRefogEntityOwner) {
        this.idRefogEntityOwner = idRefogEntityOwner;
    }

    public void setCodeEntityOwner(String codeEntityOwner) {
        this.codeEntityOwner = codeEntityOwner;
    }

    public void setLabelEntityOwner(String labelEntityOwner) {
        this.labelEntityOwner = labelEntityOwner;
    }

    public void setIdRefogEntityMOA(String idRefogEntityMOA) {
        this.idRefogEntityMOA = idRefogEntityMOA;
    }

    public void setCodeEntityMOA(String codeEntityMOA) {
        this.codeEntityMOA = codeEntityMOA;
    }

    public void setLabelEntityMOA(String labelEntityMOA) {
        this.labelEntityMOA = labelEntityMOA;
    }

    public void setIdResponsableMOE(String idResponsableMOE) {
        this.idResponsableMOE = idResponsableMOE;
    }

    public void setFirstNameResponsableMoe(String firstNameResponsableMoe) {
        this.firstNameResponsableMoe = firstNameResponsableMoe;
    }

    public void setLastNameResponsableMoe(String lastNameResponsableMoe) {
        this.lastNameResponsableMoe = lastNameResponsableMoe;
    }

    public void setCodeEntityResponsableMoe(String codeEntityResponsableMoe) {
        this.codeEntityResponsableMoe = codeEntityResponsableMoe;
    }

    public void setLabelEntityResponsableMoe(String labelEntityResponsableMoe) {
        this.labelEntityResponsableMoe = labelEntityResponsableMoe;
    }

    public void setIdRefogEntityMoe(String idRefogEntityMoe) {
        this.idRefogEntityMoe = idRefogEntityMoe;
    }

    public void setCodeEntityMoe(String codeEntityMoe) {
        this.codeEntityMoe = codeEntityMoe;
    }

    public void setLabelEntityMoe(String labelEntityMoe) {
        this.labelEntityMoe = labelEntityMoe;
    }

    public void setIdRefogEntityProduction(String idRefogEntityProduction) {
        this.idRefogEntityProduction = idRefogEntityProduction;
    }

    public void setCodeEntityProduction(String codeEntityProduction) {
        this.codeEntityProduction = codeEntityProduction;
    }

    public void setLabelEntityProduction(String labelEntityProduction) {
        this.labelEntityProduction = labelEntityProduction;
    }

    public void setCodeAttachmentUnderSector(String codeAttachmentUnderSector) {
        this.codeAttachmentUnderSector = codeAttachmentUnderSector;
    }

    public void setLabelAttachmentUnderSector(String labelAttachmentUnderSector) {
        this.labelAttachmentUnderSector = labelAttachmentUnderSector;
    }

    public void setCodeAttachmentSector(String codeAttachmentSector) {
        this.codeAttachmentSector = codeAttachmentSector;
    }

    public void setLabelAttachmentSector(String labelAttachmentSector) {
        this.labelAttachmentSector = labelAttachmentSector;
    }

    public void setCodeAttachmentPatrimony(String codeAttachmentPatrimony) {
        this.codeAttachmentPatrimony = codeAttachmentPatrimony;
    }

    public void setLabelAttachmentPatrimony(String labelAttachmentPatrimony) {
        this.labelAttachmentPatrimony = labelAttachmentPatrimony;
    }

    public void setCodeAttachmentDPP(String codeAttachmentDPP) {
        this.codeAttachmentDPP = codeAttachmentDPP;
    }

    public void setLabelAttachmentDPP(String labelAttachmentDPP) {
        this.labelAttachmentDPP = labelAttachmentDPP;
    }

    public void setLabelFunctionalCluster(String labelFunctionalCluster) {
        this.labelFunctionalCluster = labelFunctionalCluster;
    }

    public void setLabelOfSourcingMode(String labelOfSourcingMode) {
        this.labelOfSourcingMode = labelOfSourcingMode;
    }

    public void setPrivilegeInformation(String privilegeInformation) {
        this.privilegeInformation = privilegeInformation;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setRPO(String RPO) {
        this.RPO = RPO;
    }

    public void setRTO(String RTO) {
        this.RTO = RTO;
    }

    public void setIntegrity(String integrity) {
        this.integrity = integrity;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public void setTraceability(String traceability) {
        this.traceability = traceability;
    }

    public void setBusinessCriticality(String businessCriticality) {
        this.businessCriticality = businessCriticality;
    }

    public void setContinuityLevel(String continuityLevel) {
        this.continuityLevel = continuityLevel;
    }

    public void setDerogation(String derogation) {
        this.derogation = derogation;
    }

    public void setDateOfValidationOfServiceDefinition(String dateOfValidationOfServiceDefinition) {
        this.dateOfValidationOfServiceDefinition = dateOfValidationOfServiceDefinition;
    }

    public void setTypeOfSolution(String typeOfSolution) {
        this.typeOfSolution = typeOfSolution;
    }

    public void setNameOfFirmware(String nameOfFirmware) {
        this.nameOfFirmware = nameOfFirmware;
    }

    public void setTechnicalApplication(String technicalApplication) {
        this.technicalApplication = technicalApplication;
    }
}
