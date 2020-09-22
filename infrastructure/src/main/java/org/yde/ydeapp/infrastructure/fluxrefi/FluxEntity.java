package org.yde.ydeapp.infrastructure.fluxrefi;

import javax.persistence.*;

@Entity
public class FluxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @Embedded
    private JobInfoEntity jobInfoEntity;

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

}
