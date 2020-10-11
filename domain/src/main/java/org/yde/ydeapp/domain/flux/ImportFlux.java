package org.yde.ydeapp.domain.flux;

import java.time.LocalDateTime;

public class ImportFlux {
    private final String originalName;
    private final Long fluxId;
    private String location;
    private final LocalDateTime createDate;

    private FluxState fluxState;
    private Job job;
    private StatUpdateApplications statUpdateApplications;

    public ImportFlux(String orignalFileName, Long fluxId) {
        this.originalName = orignalFileName;
        this.location = "none";
        this.fluxState = FluxState.ASKED;
        this.createDate = LocalDateTime.now();
        this.fluxId = fluxId;
        this.job = new Job();
        this.statUpdateApplications = new StatUpdateApplications();
    }

    public ImportFlux(String orignalFileName, Long fluxId, FluxState fluxState, LocalDateTime createDate) {
        this.originalName = orignalFileName;
        this.location = "none";
        this.fluxState = fluxState;
        this.createDate = createDate;
        this.fluxId = fluxId;
        this.job = new Job();
        this.statUpdateApplications = new StatUpdateApplications();
    }

    public ImportFluxIdent giveFluxIdent() {
        return new ImportFluxIdent(this.getFluxId(),
            this.getOriginalName(),
            this.getFluxState(),
            this.getCreateDate(),
            this.getJob().getJobStatus());
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOriginalName() {
        return originalName;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
