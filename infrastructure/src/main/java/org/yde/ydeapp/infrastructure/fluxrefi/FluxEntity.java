package org.yde.ydeapp.infrastructure.fluxrefi;

import org.yde.ydeapp.domain.flux.FluxState;

import javax.persistence.*;

@Entity
public class FluxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private FluxState fluxState;

    private String orignalFileName;

    @Embedded
    private JobInfoEntity jobInfoEntity;

    @Embedded
    private StatUpdateEntity statUpdateEntity;

    public JobInfoEntity getJobInfoEntity() {
        return jobInfoEntity;
    }

    public void setJobInfoEntity(JobInfoEntity jobInfoEntity) {
        this.jobInfoEntity = jobInfoEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StatUpdateEntity getStatUpdateEntity() {
        return statUpdateEntity;
    }

    public void setStatUpdateEntity(StatUpdateEntity statUpdateEntity) {
        this.statUpdateEntity = statUpdateEntity;
    }

    public void setOrignalFileName(String orignalFileName) {
        this.orignalFileName = orignalFileName;
    }

    public String getOrignalFileName() {
        return orignalFileName;
    }

    public FluxState getFluxState() {
        return fluxState;
    }

    public void setFluxState(FluxState fluxState) {
        this.fluxState = fluxState;
    }
}
