package org.yde.ydeapp.domain.flux;

import java.time.LocalDateTime;

public class ImportFluxIdent {
    private Long fluxId;
    private String originalName;
    private FluxState fluxState;
    private String jobStatus;
    private LocalDateTime createDate;

    public ImportFluxIdent(Long fluxId, String originalName, FluxState fluxState, LocalDateTime createDate, String jobStatus) {
        this.fluxId = fluxId;
        this.originalName = originalName;
        this.fluxState = fluxState;
        this.jobStatus = jobStatus;
        this.createDate = createDate;
    }

    public Long getFluxId() {
        return fluxId;
    }

    public void setFluxId(Long fluxId) {
        this.fluxId = fluxId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public FluxState getFluxState() {
        return fluxState;
    }

    public void setFluxState(FluxState fluxState) {
        this.fluxState = fluxState;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
