package org.yde.ydeapp.domain.flux;

public class ImportFlux {
    private final String origninalName;
    private final Long fluxId;
    private String location;
    private Job job;
    private StatUpdateApplications statUpdateApplications;

    public ImportFlux(String originalName, Long fluxId) {
        this.origninalName = originalName;
        this.location = "none";
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
}
