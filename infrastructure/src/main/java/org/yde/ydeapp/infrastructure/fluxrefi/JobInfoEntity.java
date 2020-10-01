package org.yde.ydeapp.infrastructure.fluxrefi;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
public class JobInfoEntity {
    private int readCount;
    private String status;
    @Column(length = 2500)
    private String endStatus;
    @Column(length = 2500)
    private String resultDescription;
    private LocalDateTime startJob;
    private Duration duration;

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

    public LocalDateTime getStartJob() {
        return startJob;
    }

    public void setStartJob(LocalDateTime startJob) {
        this.startJob = startJob;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

}
