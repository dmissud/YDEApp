package org.yde.ydeapp.domain.flux;

public class ImportFlux {
    private final String origninalName;
    private final Long fluxId;
    private String location;

    private FluxState fluxState;
    private Job job;
    private StatUpdateApplications statUpdateApplications;

    public ImportFlux(String orignalFileName, Long fluxId) {
        this.origninalName = orignalFileName;
        this.location = "none";
        this.fluxState = FluxState.ASKED;
        this.fluxId = fluxId;
        this.job = new Job();
        this.statUpdateApplications = new StatUpdateApplications();
    }

    public ImportFlux(String orignalFileName, Long fluxId, FluxState fluxState) {
        this.origninalName = orignalFileName;
        this.location = "none";
        this.fluxState = fluxState;
        this.fluxId = fluxId;
        this.job = new Job();
        this.statUpdateApplications = new StatUpdateApplications();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrigninalName() {
        return origninalName;
    }

    public Long getFluxId() {
        return fluxId;
    }

    public Job getJob() {
        return job;
    }

    public StatUpdateApplications getStatUpdateApplication() {
        return statUpdateApplications;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setStatUpdateApplication(StatUpdateApplications statUpdateApplications) {
        this.statUpdateApplications = statUpdateApplications;
    }

    public FluxState getFluxState() {
        return fluxState;
    }

    public void running() {
        this.fluxState = FluxState.RUNNING;
    }

    public void complete(Job job) {
        this.fluxState = FluxState.COMPLETED;
        this.setJob(job);
    }
}
