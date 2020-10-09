package org.yde.ydeapp.domain.steps;

import org.yde.ydeapp.domain.application.*;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScenarioContext {
    private ApplicationDataTable appDescCrea;
    private ApplicationDataTable appDescUpdate;
    private ResponsableDataTable responsableDescCrea;
    private CycleDeVieDataTable cdvDescCrea;
    private final DateTimeFormatter formatter;
    private CycleDeVieDataTable cdvDescUpdate;
    private ItSolutionDataTable itsDescCrea;
    private ItSolutionDataTable itsDescUpdate;
    private CriticityDataTable criticityDescCrea;
    private CriticityDataTable criticityDescUpdate;
    private Application application;

    public ScenarioContext() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        initScenario();
    }

    public void initScenario() {
        appDescCrea = null;
        appDescUpdate = null;
        cdvDescCrea = null;
        cdvDescUpdate = null;
        responsableDescCrea = null;
        application = null;
        itsDescCrea=null;
        itsDescUpdate=null;
        criticityDescCrea=null;
        criticityDescUpdate=null;
    }

    public void setAppDescCrea(ApplicationDataTable appDescCrea) {
        this.appDescCrea = appDescCrea;
    }

    public void setAppDescUpdate(ApplicationDataTable appDescUpdate) {
        this.appDescUpdate = appDescUpdate;
    }

    public void setResponsableDescCrea(ResponsableDataTable responsableDescCrea) {
        this.responsableDescCrea = responsableDescCrea;
    }

    public void setCdvDescCrea(CycleDeVieDataTable cdvDescCrea) {
        this.cdvDescCrea = cdvDescCrea;
    }

    public void setCdvDescUpdate(CycleDeVieDataTable cdvDescUpdate) {
        this.cdvDescUpdate = cdvDescUpdate;
    }
    public void setItsDescCrea(ItSolutionDataTable itsDescCrea) {
        this.itsDescCrea = itsDescCrea;
    }
    public void setItsDescUpdate(ItSolutionDataTable itsDescUpdate) {
        this.itsDescUpdate = itsDescUpdate;
    }

    public void setCriticityDescCrea(CriticityDataTable criticityDescCrea) {
        this.criticityDescCrea = criticityDescCrea;
    }

    public void setCriticityDescUpdate(CriticityDataTable criticityDescUpdate) {
        this.criticityDescUpdate = criticityDescUpdate;
    }

    public ApplicationDataTable getAppDescCrea() {
        return appDescCrea;
    }

    public ApplicationDataTable getAppDescUpdate() {
        return appDescUpdate;
    }

    public ResponsableDataTable getResponsableDescCrea() {
        return responsableDescCrea;
    }

    public CycleDeVieDataTable getCdvDescCrea() {
        return cdvDescCrea;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public CycleDeVieDataTable getCdvDescUpdate() {
        return cdvDescUpdate;
    }

    public ItSolutionDataTable getItsDescCrea() {
        return itsDescCrea;
    }

    public ItSolutionDataTable getItsDescUpdate() {
        return itsDescUpdate;
    }


    public CriticityDataTable getCriticityDescCrea() {
        return criticityDescCrea;
    }

    public CriticityDataTable getCriticityDescUpdate() {
        return criticityDescUpdate;
    }

    public Application getApplication() {
        return application;
    }


    // ----------- build Application  ----/

    protected void buildAApplication() {
        OrganizationIdent organizationIdent = new OrganizationIdent(appDescCrea.getIdRefogOrganization(), "Organization Name");
        Personne personne = buildPersonne(responsableDescCrea);
        CycleLife cycleLife = buildCycleLife(cdvDescCrea, formatter);
        ItSolution itSolution=buildItSolution(itsDescCrea);
        Criticity criticity=buildCriticity((criticityDescCrea));
        application = new Application.Builder(appDescCrea.getCodeApplication())
            .withShortDescription(appDescCrea.getShortDescription())
            .withLongDescription(appDescCrea.getLongDescription())
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .withItSolution(itSolution)
            .withCriticity(criticity)
            .build();
    }


    // ----------- build Cycle life  ----/

    public CycleLife buildCycleLifeForUpdate() {
        return buildCycleLife(cdvDescUpdate, formatter);
    }
    private static CycleLife buildCycleLife(CycleDeVieDataTable cdvDesc, DateTimeFormatter formatter) {

        LocalDate dateCreation=null;
        LocalDate dateOfLastUpdate=null;
        LocalDate dateEndInReality=null;

        if (cdvDesc.getDateOfCreation() !=null) {

            dateCreation = LocalDate.parse(cdvDesc.getDateOfCreation(), formatter);
        }
        if (cdvDesc.getDateOfLastUpdate() != null) {

            dateOfLastUpdate = LocalDate.parse(cdvDesc.getDateOfLastUpdate(), formatter);
        }
        if (cdvDesc.getDateEndInReality() != null) {


            dateEndInReality = LocalDate.parse(cdvDesc.getDateEndInReality(), formatter);
        }

        return new CycleLife(cdvDesc.getState(),
            dateCreation,
            dateOfLastUpdate,
            dateEndInReality);
    }


    // ----------- build Personne  ----/

    private static Personne buildPersonne(ResponsableDataTable responsableDesc) {
        return new Personne(responsableDesc.getUid(), responsableDesc.getFirstName(), responsableDesc.getLastName());
    }


    // ----------- build IT solution  ----/

    public ItSolution buildItSolutionForUpdate() {
        return buildItSolution(itsDescUpdate);
    }

    private static ItSolution buildItSolution(ItSolutionDataTable itSolutionDesc) {
        return new ItSolution(itSolutionDesc.getTypeOfSolution(),itSolutionDesc.getNameOfFirmware(),itSolutionDesc.getLabelOfSourcingMode());
    }


    // ----------- build Criticity  ----/

    public Criticity buildCriticityForUpdate() {
        return buildCriticity(criticityDescUpdate);
    }


    private static Criticity buildCriticity(CriticityDataTable criticityDesc) {
        return new Criticity(criticityDesc.getPrivilegeInformation(),
                criticityDesc.getPersonalData(),
                criticityDesc.getServiceClass(),
                criticityDesc.getAviability(),
                criticityDesc.getRpo(),
                criticityDesc.getRto());
    }
}
