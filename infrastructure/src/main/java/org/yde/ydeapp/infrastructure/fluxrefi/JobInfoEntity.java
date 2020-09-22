package org.yde.ydeapp.infrastructure.fluxrefi;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class JobInfoEntity {
    private String status;
    private String endStatus;
    private String resultDescription;
    private Date startJob;
    private Date endJob;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(String endStatus) {
        this.endStatus = endStatus;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public Date getStartJob() {
        return startJob;
    }

    public void setStartJob(Date start) {
        this.startJob = start;
    }

    public Date getEndJob() {
        return endJob;
    }

    public void setEndJob(Date end) {
        this.endJob = end;
    }
}
