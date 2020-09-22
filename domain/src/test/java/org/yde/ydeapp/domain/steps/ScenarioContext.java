package org.yde.ydeapp.domain.steps;

import org.yde.ydeapp.domain.application.Application;
import org.yde.ydeapp.domain.application.CycleLife;
import org.yde.ydeapp.domain.organization.OrganizationIdent;
import org.yde.ydeapp.domain.application.Personne;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScenarioContext {
    private ApplicationDataTable appDescCrea;
    private ApplicationDataTable appDescUpdate;
    private ResponsableDataTable responsableDescCrea;
    private CycleDeVieDataTable cdvDescCrea;
    private final DateTimeFormatter formatter;
    private CycleDeVieDataTable cdvDescUpdate;
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

    public Application getApplication() {
        return application;
    }

    protected void buildAApplication() {
        OrganizationIdent organizationIdent = new OrganizationIdent(appDescCrea.getIdRefogOrganization(), "Organization Name");
        Personne personne = buildPersonne(responsableDescCrea);
        CycleLife cycleLife = buildCycleLife(cdvDescCrea, formatter);
        application = new Application.Builder(appDescCrea.getCodeApplication())
            .withShortDescription(appDescCrea.getShortDescription())
            .withLongDescription(appDescCrea.getLongDescription())
            .withResponsable(personne)
            .withOrganization(organizationIdent)
            .withCycleLife(cycleLife)
            .build();
    }

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

    private static Personne buildPersonne(ResponsableDataTable responsableDesc) {
        return new Personne(responsableDesc.getUid(), responsableDesc.getFirstName(), responsableDesc.getLastName());
    }

}
